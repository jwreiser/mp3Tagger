package com.goodforallcode.mp3Tagger.service;


import com.goodforallcode.mp3Tagger.model.domain.Mp3Info;
import com.goodforallcode.mp3Tagger.musicbrainz.MusicBrainzCallResults;
import com.goodforallcode.mp3Tagger.rest.MusicBrainzRestCaller;
import com.goodforallcode.mp3Tagger.util.FileNameUtil;
import com.goodforallcode.mp3Tagger.util.Mp3FileUtil;
import com.goodforallcode.mp3Tagger.util.StringUtil;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.goodforallcode.mp3Tagger.util.Mp3FileUtil.updateFileTags;

public class SongInformationService {
    static MusicBrainzRestCaller caller = new MusicBrainzRestCaller();


    public Set<Mp3Info> getSongInformation(Set<Mp3Info> songs, String currentDirectory, boolean manuallyConfigureMp3Tags) {
        Set<Mp3Info> fileList = new HashSet<>();
        try {
            final long numFiles = getNumberOfSongs(currentDirectory);

            final double share = 1.0 / numFiles;
            Files.walkFileTree(Paths.get(currentDirectory), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (!Files.isDirectory(file)) {
//TODO                    task.exposedUpdateProgress(task.getProgress() + share,numFiles);
//                        if(progressBar!=null) {
//                            progressBar.setProgress(progressBar.getProgress() + share);
//                        }
                        if (file.getFileName().toString().endsWith("mp3")) {
                            Mp3Info info = fileToInfo(file, manuallyConfigureMp3Tags);
                            if (info != null) {
                                songs.add(info);
                            }
                        }
                    }else {
                        System.out.println("h");
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException ex) {

        }

        return fileList;
    }

    public long getNumberOfSongs(String currentDirectory) throws IOException {
        Path directory = Path.of(currentDirectory);
        return Files.walk(directory).parallel().filter(p -> !p.toFile().isDirectory() && p.getFileName().toString().endsWith(".mp3")).count();
    }

    public Mp3Info fileToInfo(Path file, boolean manuallyConfigureMp3Tags) {
        Mp3Info result = Mp3FileUtil.getMp3Info(file);
        String album = StringUtil.cleanupAlbum(file.getParent().getFileName().toString());
        String trackName = FileNameUtil.getCleanFileName(file);
        if (trackName.endsWith(" UPDATED")) {
            return null;
        }
        String artist = null;
        Integer trackNumber = null;
        Integer numTracks = null;
        if (List.of("misc", "mixed", "various", "various artists").contains(album.toLowerCase()) && trackName.contains(" - ")) {
            String testTrack = trackName = FileNameUtil.getCleanFileName(file, true);
            String[] parts = trackName.split(" - ");
            if (parts.length == 2) {
                trackName = parts[0];
                artist = parts[1];
                album = null;
            }
        } else {
            artist = file.getParent().getParent().getFileName().toString();
        }
        if (result == null || !result.isComplete()) {
            lookupMp3Tags(file, album, trackName, artist, manuallyConfigureMp3Tags);
        }
        return result;
    }

    private Mp3Info lookupMp3Tags(Path file, String album, String trackName, String artist, boolean manuallyConfigureMp3Tags) {
        Long duration = Mp3FileUtil.getMp3Duration(file);


        Mp3Info result = getMp3Info(file, trackName, album, artist, manuallyConfigureMp3Tags);
        if (result != null) {
            Integer genre = null;
            if (result.getGenreDescription() != null) {
                genre = result.getGenre();
            }
            updateFileTags(file, result.getAlbum(), result.getArtist(), result.getTitle(), genre, null);
        } else {
            //use existing file information since it seems like it is all we are going to get
            String existingTrackName = null;
            if (trackName != null) {
                existingTrackName = trackName.toLowerCase().replace("track", "").replaceAll("\\d", "");
            }
            if (existingTrackName != null && existingTrackName.length() > 5) {
                updateFileTags(file, album, artist, trackName, null, null);
            } else if (manuallyConfigureMp3Tags) {
                System.err.println("could not map " +file.toAbsolutePath().toString()+"~"+artist+"~"+album);
            }
        }


        return result;
    }


    private static boolean weHaveBadTagData(Integer tracknum, Integer numTracks) {
        return tracknum != null && numTracks != null;
    }

    private static Mp3Info getMp3Info(String suggestedTrack, Integer tracknum, Integer numTracks, String album, String artist, long duration
            , boolean manuallyConfigureMp3Tags) {
        MusicBrainzCallResults results = null;
        if (suggestedTrack != null) {
            suggestedTrack = suggestedTrack.toLowerCase().replace("track", "");
            if (suggestedTrack.length() > 5) {
                results = caller.getInfoFromArtistAndTrack(suggestedTrack, artist, "jreiser.is@gmail.com");
            }
        }
        if (results == null) {
            results = caller.getInfoFromArtistAndAlbum(artist, album, "jreiser.is@gmail.com");
        }
        Mp3Info result = null;
        if (results != null) {
            result = results.getMp3InfoFromResults(suggestedTrack, tracknum, numTracks, duration);
        }
        return result;
    }

    private static Mp3Info getMp3Info(Path file, String trackName, String album, String artist, boolean manuallyConfigureMp3Tags) {
        MusicBrainzCallResults results = null;
        Mp3Info result = null;

        if (album != null) {
            results = caller.getInfoFromFullInformation(trackName, album, artist, "jreiser.is@gmail.com");
        } else {
            results = caller.getInfoFromArtistAndTrack(trackName, artist, "jreiser.is@gmail.com");
        }

        if (results != null) {
            result = results.getMp3InfoFromResults();
        }
        if (result == null) {
            if (album != null) {
                results = caller.getInfoFromAlbum(trackName, album, "jreiser.is@gmail.com");
                if(results!=null) {
                    result = results.getMp3InfoFromResults();
                }
                if (result == null) {//since album is not null we did not try this yet
                    results = caller.getInfoFromArtistAndTrack(trackName, artist, "jreiser.is@gmail.com");
                    if(results != null) {
                        result = results.getMp3InfoFromResults();
                    }
                }
            }

        }
        if (result == null) {
            results = caller.getInfoFromTrack(trackName, "jreiser.is@gmail.com");
            if (results!=null && results.getCount() == 1) {
                result = results.getMp3InfoFromResults();
            } else if (manuallyConfigureMp3Tags) {
                System.err.println("could not map" +file.toAbsolutePath().toString()+"~"+artist+"~"+ album);

            }
        }
        return result;
    }
}
