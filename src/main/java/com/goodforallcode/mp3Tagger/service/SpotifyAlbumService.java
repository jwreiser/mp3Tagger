package com.goodforallcode.mp3Tagger.service;


import com.goodforallcode.mp3Tagger.model.domain.Album;
import com.goodforallcode.mp3Tagger.model.domain.spotify.SpotifyAlbumItem;
import com.goodforallcode.mp3Tagger.spotify.SpotifyRestCaller;
import com.goodforallcode.mp3Tagger.util.FileUtil;
import com.goodforallcode.mp3Tagger.util.Mp3FileUtil;
import com.google.common.collect.Lists;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.special.SnapshotResult;
import com.wrapper.spotify.requests.data.playlists.AddTracksToPlaylistRequest;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SpotifyAlbumService {
    PlaylistGenerationService service = new PlaylistGenerationService();
    private static SpotifyRestCaller spotifyCaller = new SpotifyRestCaller();


    public List<Album> tagAlbumTracks(String token, List<Album> allAlbums) throws URISyntaxException {
        if (!allAlbums.isEmpty()) {
            findAlbums_IfNotPresentLookup(allAlbums, token);
            addAlbumTrackDetails_IfNotPresentLookThemUp(token, allAlbums);
        }
        return allAlbums;
    }


    public boolean publishTracks(String userName, String token, String playlistId, Collection<File> fileList, SpotifyApi spotifyApi) {
        boolean success = true;
        String[] uriArray;
        AddTracksToPlaylistRequest request;
        SnapshotResult snapshotResult;
        List<String> trackIds;
        String comment;

        List<String> uris = uris = new ArrayList<>();
        trackIds = getTrackIds(fileList);
        if (!trackIds.isEmpty()) {
            for (String trackId : trackIds) {
                uris.add("spotify:track:" + trackId);
            }
            try {
                uriArray = (String[]) uris.toArray(new String[0]);
                if(uriArray.length>0){
                    request = spotifyApi.addTracksToPlaylist(userName, playlistId, uriArray).build();
                    snapshotResult = request.execute();
                }else{
                    System.err.println("No tracks to add to playlist for"+userName+" "+playlistId+" "+fileList.toString());
                }
            } catch (IOException | StringIndexOutOfBoundsException e) {
                throw new RuntimeException(e);
            } catch (SpotifyWebApiException e) {
                success = false;
                //this could be a wrong track id
                List<File> singleFileList;
                if (fileList.size() > 1) {
                    for (File file : fileList) {
                        if (!Mp3FileUtil.alreadyRetried(file)) {//Only retry once
                            singleFileList = new ArrayList<>();
                            singleFileList.add(file);
                            if (!publishTracks(userName, token, playlistId, singleFileList, spotifyApi)) {
                                Mp3FileUtil.setStatusToRetried(file);
                            }
                        } else {
                            System.err.println("Failed to upload " + file.toPath().toString());
                        }
                    }
                }
            }
        }
        return success;
    }

    private List<String> getTrackIds(Collection<File> files) {
        String trackId;
        Mp3File mp3;
        ID3v1 tag;

        List<String> trackIds = new ArrayList<>();
        for (File file : files) {
            try {
                mp3 = new Mp3File(file);
                tag = Mp3FileUtil.getTag(mp3);
                if (tag.getComment().contains("spotifyTrackId")) {
                    for (String comment : tag.getComment().split(";")) {
                        if (comment.contains("spotifyTrackId")) {
                            trackId = comment.split(":")[1];
                            trackIds.add(trackId);
                            break;
                        }
                    }
                } else {
                    System.err.println("Missing track id for " + file.toPath().toString());
                }
            } catch (IOException | UnsupportedTagException | InvalidDataException e) {
                //skip file
            }

        }

        return trackIds;
    }

    /**
     * Preforms an album search and if there are no hits does playlist search and track search until all possible tracks
     * have spotify details.
     * <p>
     * Returns all albums that are on spotify
     */
    private static List<Album> findAlbums_IfNotPresentLookup(List<Album> albumsMissingDetails, String token) {
        SpotifyAlbumItem currentAlbumItem = null;
        boolean isCompilation,isVariousArtists;
        List<Album> albumsToGetDetailsFor = new ArrayList<>();
        for (Album currentAlbum : albumsMissingDetails) {
            isCompilation = FileUtil.isCompilation(currentAlbum);
            isVariousArtists = FileUtil.isVariousArtists(currentAlbum);
            if (currentAlbum.getName() != null) {
                currentAlbumItem = spotifyCaller.getAlbum(currentAlbum.getArtist(), currentAlbum.getName(), token, isCompilation,isVariousArtists);
            }
            if (currentAlbumItem != null) {
                currentAlbum.setAlbumItem(currentAlbumItem);
                albumsToGetDetailsFor.add(currentAlbum);
            } else {
                System.err.println("Could not find album for " + currentAlbum.getArtist() + " " + currentAlbum.getName());
                if (currentAlbum.getName() == null || !spotifyCaller.addPlaylistSearchInformationToTracks(currentAlbum.getArtist(), currentAlbum.getName(), token,  isVariousArtists,currentAlbum)) {
                    spotifyCaller.populateAlbumWithTrackSearch(currentAlbum, token,isVariousArtists);
                }
            }
        }
        return albumsToGetDetailsFor;
    }

    private static void addAlbumTrackDetails_IfNotPresentLookThemUp(String token, List<Album> albumsToGetDetailsFor) {
        ID3v1 tag;
        Mp3File mp3;
        Collection<List<Album>> lists = Lists.partition(albumsToGetDetailsFor, 20);
        for (List<Album> list : lists) {
            boolean finishedMapping = spotifyCaller.populateAlbumsWithAdditionalSpotifyInformation(list, token);
            if (!finishedMapping) {
                for (Album album : list) {
                    for (File file : album.getFiles()) {
                        if (!Mp3FileUtil.fileHasSpotifyInformation(file)) {
                            try {
                                mp3 = new Mp3File(file);
                                tag = Mp3FileUtil.getTag(mp3);
                                spotifyCaller.populateFileWithTrackSearch(tag.getArtist(), tag.getTitle(), token, file.toPath(), album, mp3, tag);
                            } catch (IOException | UnsupportedTagException | InvalidDataException e) {
                                //skip file
                            }
                        }

                    }
                }
            }
        }
    }

}
