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

    public List<Album> addDirectoriesToPlayList(List<Path> pathList, String token, boolean disambiguate) {
        List<Album> updatedAlbums = null;
        List<Album> workingAlbums = new ArrayList<>();
        for (
                Path path : pathList) {
            final String directoryPath = path.toAbsolutePath().toString();
            Set<Mp3Info> songs = new HashSet<>();
            songService.getSongInformation(songs, directoryPath, true);
            List<Album> albums = musicBrainzAlbumService.getAlbums(directoryPath, disambiguate);
            for (Album album : albums) {
                if (!Mp3FileUtil.isEveryFileTagged(album)) {
                    System.out.println("still need to tag songs in: " + album.getName());
                    workingAlbums.add(album);
                } else {
                    System.out.println("Already tagged every song in: " + album.getName());
                }
            }
        }
        if (!workingAlbums.isEmpty()) {
            try {
                updatedAlbums = spotifyAlbumService.addAlbumsToPlaylist(token, workingAlbums);
            } catch (
                    URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        return updatedAlbums;
    }
}
