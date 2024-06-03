package com.goodforallcode.mp3Tagger.service;


import com.goodforallcode.mp3Tagger.model.domain.Album;
import com.goodforallcode.mp3Tagger.model.domain.spotify.SpotifyAlbumItem;
import com.goodforallcode.mp3Tagger.spotify.SpotifyRestCaller;
import com.goodforallcode.mp3Tagger.util.Mp3FileUtil;
import com.google.common.collect.Lists;
import com.mpatric.mp3agic.*;
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
    PlaylistGenerationService service=new PlaylistGenerationService();
    private static SpotifyRestCaller spotifyCaller = new SpotifyRestCaller();

    public List<Album> addAlbumsToPlaylist(String token, List<Album> allAlbums) throws URISyntaxException {
        if (!allAlbums.isEmpty()) {
            findAlbums_IfNotPresentLookup(allAlbums, token);
            addAlbumTrackDetails_IfNotPresentLookThemUp(token, allAlbums);
        }
        return allAlbums;
    }

    public boolean publishTracks(String userName, String token,String playlistId, Collection<List<File>> filesLists, SpotifyApi spotifyApi, boolean manuallyConfigureMp3Tags, boolean disambiguate) {
        boolean success=true;

        for (List<File> fileList : filesLists) {
            if(!publishTracksWorker(userName,token,playlistId,fileList,spotifyApi,manuallyConfigureMp3Tags,disambiguate)){
                success=false;
            }
        }
        return success;
    }
    private boolean publishTracksWorker(String userName, String token,String playlistId, List<File> fileList, SpotifyApi spotifyApi
            , boolean manuallyConfigureMp3Tags, boolean disambiguate) {
        boolean success=true;
        String[] uriArray;
        AddTracksToPlaylistRequest request;
        SnapshotResult snapshotResult;
        List<String> trackIds;
        String comment;

        List<String> uris = uris = new ArrayList<>();
        trackIds = getTrackIds(fileList);
        for (String trackId : trackIds) {
            uris.add("spotify:track:" + trackId);
        }
        try {
            uriArray = (String[]) uris.toArray(new String[0]);
            request = spotifyApi.addTracksToPlaylist(userName, playlistId, uriArray).build();
            snapshotResult = request.execute();
            for (File file : fileList) {
                Mp3FileUtil.addCommentToFile(file);
            }
            System.err.println("Published " + uris.size() + " songs");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SpotifyWebApiException e) {
            success=false;
            //this could be a wrong track id
            List<File> singleFileList;
            List<File> failedList=new ArrayList<>();
            if (fileList.size() > 1){
                for(File file:fileList) {
                    if(!Mp3FileUtil.currentlyRetrying(file)) {
                        singleFileList = new ArrayList<>();
                        singleFileList.add(file);
                        if (!publishTracksWorker(userName, token, playlistId, singleFileList, spotifyApi, manuallyConfigureMp3Tags, disambiguate)) {
                            failedList.add(file);
                            Mp3FileUtil.setStatusToRetry(file);
                        }
                    }else{
                        Mp3FileUtil.setStatusToUploadFailed(file);
                    }
                }
                service.addDirectoriesToPlayList(List.of(failedList.get(0).toPath().getParent()),token,disambiguate);

            }else{
                Mp3FileUtil.setStatusToUploadFailed(fileList.get(0));
            }

        }
        return success;
    }

    private List<String> getTrackIds(List<File> files) {
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
     *
     * Returns all albums that are on spotify
     */
    private static List<Album> findAlbums_IfNotPresentLookup(List<Album> albumsMissingDetails, String token) {
        SpotifyAlbumItem currentAlbumItem=null;
        boolean isCompilation;
        List<Album> albumsToGetDetailsFor = new ArrayList<>();
        for (Album currentAlbum : albumsMissingDetails) {
            if (currentAlbum.getFiles().size() < 4) {
                isCompilation = true;
            } else {
                isCompilation = false;
            }


            if(currentAlbum.getName()!=null) {
                currentAlbumItem = spotifyCaller.getAlbum(currentAlbum.getArtist(), currentAlbum.getName(), token, isCompilation);
            }
            if (currentAlbumItem != null) {
                currentAlbum.setAlbumItem(currentAlbumItem);
                albumsToGetDetailsFor.add(currentAlbum);
            } else {
                if (currentAlbum.getName()==null || !spotifyCaller.addPlaylistInformationToTracks(currentAlbum.getArtist(), currentAlbum.getName(), token, isCompilation, currentAlbum)) {
                    spotifyCaller.populateAlbumWithTrackSearch(currentAlbum, token);
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
            spotifyCaller.populateAlbumsWithAdditionalSpotifyInformation(list, token);
            for (Album album : list) {
                for (File file : album.getFiles()) {
                    if (!Mp3FileUtil.fileTagged(file)) {
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
