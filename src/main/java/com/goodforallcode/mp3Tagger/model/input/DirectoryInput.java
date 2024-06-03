package com.goodforallcode.mp3Tagger.model.input;

public class DirectoryInput {
    private String directory;
    public DirectoryInput(){

    }

    public DirectoryInput(String directory) {
        this.directory = directory;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

}
