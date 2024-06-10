package com.goodforallcode.mp3Tagger.batch;



import com.goodforallcode.mp3Tagger.model.domain.Mp3Info;
import com.goodforallcode.mp3Tagger.model.input.DirectoryInput;
import com.goodforallcode.mp3Tagger.service.PlaylistGenerationService;
import com.goodforallcode.mp3Tagger.service.SpotifyAlbumService;
import com.goodforallcode.mp3Tagger.util.FileUtil;
import com.google.common.collect.Lists;
import org.springframework.batch.item.ItemProcessor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class TaggingProcessor implements ItemProcessor<DirectoryInput, DirectoryInput> {
    static             PlaylistGenerationService service = new PlaylistGenerationService();
    static SpotifyAlbumService spotifyAlbumService = new SpotifyAlbumService();
    String token;

    public TaggingProcessor(String token) {
        this.token = token;
    }


    @Override
    public DirectoryInput process(DirectoryInput item) throws Exception {
        Path directory = Paths.get(item.getDirectory());

        List<Path> pathList = new FileUtil().getDirectories(directory);


        if(!pathList.isEmpty()) {
            service.addDirectoriesToPlayList(pathList, token);
        }

        return item;

    }
}
