package com.goodforallcode.mp3Tagger.service;


import com.goodforallcode.mp3Tagger.model.domain.Album;
import com.goodforallcode.mp3Tagger.model.domain.Mp3Info;
import com.goodforallcode.mp3Tagger.util.Mp3FileUtil;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlaylistGenerationService {
    static SongInformationService songService = new SongInformationService();
    static MusicBrainzAlbumService musicBrainzAlbumService = new MusicBrainzAlbumService();
    static SpotifyAlbumService spotifyAlbumService = new SpotifyAlbumService();

    public List<Mp3Info> getMp3InfoList(List<Path> pathList) {
        List<Mp3Info> mp3InfoList = new ArrayList<>();
        for (
                Path path : pathList) {
            final String directoryPath = path.toAbsolutePath().toString();
            mp3InfoList.addAll(songService.getCurrentSongInformation(directoryPath));
        }
        return mp3InfoList;
    }
    public void addDirectoriesToPlayList(List<Path> pathList, String token) {
        List<Album> workingAlbums = getAlbumsThatNeedTagging(pathList);
        if (!workingAlbums.isEmpty()) {
            try {
                spotifyAlbumService.tagAlbumTracks(token, workingAlbums);
            } catch (
                    URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private static List<Album> getAlbumsThatNeedTagging(List<Path> pathList) {
        List<Album> workingAlbums = new ArrayList<>();

        for (Path path : pathList) {
            final String directoryPath = path.toAbsolutePath().toString();

            List<Album> albums = musicBrainzAlbumService.getAlbums(directoryPath);
            for (Album album : albums) {
                if (!Mp3FileUtil.doesEveryFileHaveSpotifyInformation(album)) {
                    System.err.println("still need to tag songs in: " + album.getArtist()+"~"+album.getName());
                    workingAlbums.add(album);
                } else {
                    System.out.println("Already tagged every song in: " +album.getArtist()+"~"+ album.getName());
                }
            }
        }
        return workingAlbums;
    }
}
