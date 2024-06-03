package com.goodforallcode.mp3Tagger.util;


import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
public class FileUtil {


    public Collection<List<Path>> getPathList(List<Path> directories) throws IOException {

        List<Path> leafDirectories = new ArrayList<>();
        for (Path currentDirectory : directories) {
            leafDirectories.addAll(getPathList(currentDirectory));
        }
        Collection<List<Path>> pathLists = breakListIntoThreadSizeChunks(leafDirectories, 12);
        return pathLists;
    }

    public List<Path> getPathList(Path currentDirectory) throws IOException {

        List<Path> leafDirectories = new ArrayList<>();
        boolean leafDirectory;
        final String directoryPath = currentDirectory.toAbsolutePath().toString();
        leafDirectory = !Files.walk(currentDirectory, 1).anyMatch(f -> isDirectory(f, directoryPath));
        if (leafDirectory) {
            if(Mp3FileUtil.isEveryFileTagged(currentDirectory)){
                System.out.println("Every file tagged in " + currentDirectory.toAbsolutePath().toString());
            }else {
                leafDirectories.add(currentDirectory);
            }
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
}
