package com.goodforallcode.mp3Tagger.batch;



import com.goodforallcode.mp3Tagger.model.domain.Mp3Info;
import com.goodforallcode.mp3Tagger.model.input.DirectoryInput;
import com.goodforallcode.mp3Tagger.service.PlaylistGenerationService;
import com.goodforallcode.mp3Tagger.service.SpotifyAlbumService;
import com.goodforallcode.mp3Tagger.util.FileUtil;
import com.wrapper.spotify.SpotifyApi;
import org.springframework.batch.item.ItemProcessor;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

/**
 * Used to add mp3 information to the playlist
 */
public class Mp3InfoPublishingProcessor implements ItemProcessor<DirectoryInput, Void> {
    static SpotifyAlbumService spotifyAlbumService = new SpotifyAlbumService();

    String token;
    String playlistId;
    SpotifyApi spotifyApi;
    public Mp3InfoPublishingProcessor(String token,String playlistId,SpotifyApi spotifyApi) {
        this.token = token;
        this.playlistId=playlistId;
        this.spotifyApi=spotifyApi;
    }

    @Override
    public Void process(DirectoryInput item) throws Exception {
        Path directory = Paths.get(item.getDirectory());
        Set<File> files = new FileUtil().getFiles(directory);
        if(!files.isEmpty()) {
            spotifyAlbumService.publishTracks("savecuomo", token, playlistId, files, spotifyApi);
        }
        return null;
    }
}
