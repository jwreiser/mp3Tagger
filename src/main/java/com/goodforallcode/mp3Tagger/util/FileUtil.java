package com.goodforallcode.mp3Tagger.util;


import com.goodforallcode.mp3Tagger.model.domain.Album;
import com.goodforallcode.mp3Tagger.model.domain.Mp3Info;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
public class FileUtil {


    public List<Path> getDirectories(Path currentDirectory) throws IOException {

        List<Path> leafDirectories = new ArrayList<>();
        boolean leafDirectory;
        final String directoryPath = currentDirectory.toAbsolutePath().toString();
        leafDirectory = !Files.walk(currentDirectory, 1).anyMatch(f -> isDirectory(f, directoryPath));
        if (leafDirectory) {
            if(Mp3FileUtil.doesEveryFileHaveSpotifyInformation(currentDirectory)){
                System.out.println("Every file tagged in " + currentDirectory.toAbsolutePath().toString());
            }else {
                leafDirectories.add(currentDirectory);
            }
        }
        return leafDirectories;
    }

    public Set<File> getFiles(Path currentDirectory) throws IOException {

        final String directoryPath = currentDirectory.toAbsolutePath().toString();
        Set<File> files = Files.walk(currentDirectory, 1).filter(f -> !f.toFile().isDirectory() && f.getFileName().toString().endsWith("mp3")).map(p->p.toFile()).collect(Collectors.toSet());
        return files;
    }

    public List<Path> getFullPathList(Path currentDirectory) throws IOException {

        List<Path> leafDirectories = new ArrayList<>();
        boolean leafDirectory;
        final String directoryPath = currentDirectory.toAbsolutePath().toString();
        leafDirectory = !Files.walk(currentDirectory, 1).anyMatch(f -> isDirectory(f, directoryPath));
        if (leafDirectory) {
            leafDirectories.add(currentDirectory);
        }
        return leafDirectories;
    }

    private static Collection<List<Path>> breakListIntoThreadSizeChunks(List<Path> list, int numThreads) {
        List<List<Path>> results = new ArrayList<List<Path>>();
        for (int i = 0; i < numThreads; i++) {
            results.add(new ArrayList<>());
        }
        List<Path> currentList;
        int currentListIndex;
        for (int i = 0; i < list.size(); i++) {
            currentListIndex = i % numThreads;
            currentList = results.get(currentListIndex);
            currentList.add(list.get(i));
        }
        /*int desiredListSize = 1;
        if (list.size() > numThreads) {
            desiredListSize = list.size() / numThreads;
        }
        did not disperse evenly Collection<List<Path>> lists = Lists.partition(list, desiredListSize);

         */

        return results;
    }

    private boolean isDirectory(Path file, String directoryPath) {
        if (!file.toFile().isDirectory()) {
            return false;
        } else if (file.toAbsolutePath().toString().equals(directoryPath)) {
            return false;//should not count the current directory
        } else {
            return true;
        }
    }

    public static boolean isCompilation(Album album) {
         long count=album.getFiles().stream().filter(f -> isCompilation(f)).count();
         float percentage=(float)count/(float)album.getFiles().size();
        return percentage>0.75;
    }

    public static boolean isCompilation(File file) {
        boolean result=false;
        if(isVariousArtists(file)) {
            return true;
        }
        Mp3Info mp3Info = Mp3FileUtil.getMp3Info(file);
        String album=mp3Info.getAlbum();
        if(album.contains("greatest")||album.contains("hits")||album.contains("best of ")) {
            result=true;
        }
        return result;
    }

    public static boolean isVariousArtists(Album album) {
        long count=album.getFiles().stream().filter(f -> isVariousArtists(f)).count();
        float percentage=(float)count/(float)album.getFiles().size();
        return percentage>0.5;
    }

    public static boolean isVariousArtists(File file) {
        boolean result=false;
        Mp3Info mp3Info = Mp3FileUtil.getMp3Info(file);
        String album=mp3Info.getAlbum();
        if(album.contains("various")) {
            result=true;
        }
        return result;
    }
}
