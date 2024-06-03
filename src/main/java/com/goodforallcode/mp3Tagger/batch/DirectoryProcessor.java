package com.goodforallcode.mp3Tagger.batch;



import com.goodforallcode.mp3Tagger.model.domain.Album;
import com.goodforallcode.mp3Tagger.model.input.DirectoryInput;
import com.goodforallcode.mp3Tagger.service.PlaylistGenerationService;
import com.goodforallcode.mp3Tagger.service.SpotifyAlbumService;
import com.goodforallcode.mp3Tagger.util.FileUtil;
import com.google.common.collect.Lists;
import org.springframework.batch.item.ItemProcessor;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

public class DirectoryProcessor implements ItemProcessor<DirectoryInput, Void> {
    static             PlaylistGenerationService service = new PlaylistGenerationService();
    static SpotifyAlbumService spotifyAlbumService = new SpotifyAlbumService();
    String token;

    public DirectoryProcessor(String token) {
        this.token = token;
    }


    @Override
    public Void process(DirectoryInput item) throws Exception {
        Path directory = Paths.get(item.getDirectory());

        List<Path> pathList = new FileUtil().getPathList(directory);
//        System.err.println("Got path list for " + item.getDirectory() + ". Size:" + pathList.size());

        if(!pathList.isEmpty()) {
            List<Album> allAlbums = service.addDirectoriesToPlayList(pathList, token, true);
        }

        return null;
    }
}
