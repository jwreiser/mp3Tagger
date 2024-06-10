package com.goodforallcode.mp3Tagger.batch;



import com.goodforallcode.mp3Tagger.model.domain.Mp3Info;
import com.goodforallcode.mp3Tagger.model.input.DirectoryInput;
import com.goodforallcode.mp3Tagger.service.PlaylistGenerationService;
import com.goodforallcode.mp3Tagger.util.FileUtil;
import org.springframework.batch.item.ItemProcessor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Used to add mp3 information to the database
 */
public class Mp3InfoToDatabaseProcessor implements ItemProcessor<DirectoryInput, List<Mp3Info>> {
    static             PlaylistGenerationService service = new PlaylistGenerationService();


    @Override
    public List<Mp3Info> process(DirectoryInput item) throws Exception {
        Path directory = Paths.get(item.getDirectory());
        List<Path> pathList = new FileUtil().getFullPathList(directory);
        return service.getMp3InfoList(pathList);

    }
}
