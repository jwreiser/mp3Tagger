package com.goodforallcode.mp3Tagger.model.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mp3Info {
    String title;
    String artist;
    String album;
    String fileLocation;
    int count;
    int genre;
    String genreDescription;
    String comment;

    public boolean isUploaded() {
        return comment!=null && comment.contains("spotifyTrackId");
    }



    public Mp3Info(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    public Mp3Info(String title, String artist, String album) {
        this.title = title;
        this.artist = artist;
        this.album = album;
    }

    public Mp3Info(String title, String artist, String album,String comment) {
        this(title, artist, album);
        this.comment = comment;
    }

    public Mp3Info(String title, String artist, String album, int genre, String genreDescription) {
        this(title, artist, album);
        this.genre = genre;
        this.genreDescription = genreDescription;
    }



    @Override
    public String toString() {

        return title + " - " + artist;//+" - "+album;

    }

    public String getTitle() {
        return this.title;
    }

    public String getArtist() {
        return this.artist;
    }

    public String getAlbum() {
        return this.album;
    }

    public int getGenre() {
        return this.genre;
    }

    public String getGenreDescription() {
        return this.genreDescription;
    }
    public String getComment() {
        return this.comment;
    }

    public boolean isComplete() {
        return getArtist()!=null && getAlbum()!=null && getTitle()!=null
                && !getArtist().isEmpty() && !getAlbum().isEmpty() && !getTitle().isEmpty();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public void setGenreDescription(String genreDescription) {
        this.genreDescription = genreDescription;
    }


    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Mp3Info)) return false;
        final Mp3Info other = (Mp3Info) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$title = this.getTitle();
        final Object other$title = other.getTitle();
        if (this$title == null ? other$title != null : !this$title.equals(other$title)) return false;
        final Object this$artist = this.getArtist();
        final Object other$artist = other.getArtist();
        if (this$artist == null ? other$artist != null : !this$artist.equals(other$artist)) return false;
        final Object this$album = this.getAlbum();
        final Object other$album = other.getAlbum();
        if (this$album == null ? other$album != null : !this$album.equals(other$album)) return false;
        if (this.getGenre() != other.getGenre()) return false;
        final Object this$genreDescription = this.getGenreDescription();
        final Object other$genreDescription = other.getGenreDescription();
        if (this$genreDescription == null ? other$genreDescription != null : !this$genreDescription.equals(other$genreDescription))
            return false;
        if (this.isComplete() != other.isComplete()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Mp3Info;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $title = this.getTitle();
        result = result * PRIME + ($title == null ? 43 : $title.hashCode());
        final Object $artist = this.getArtist();
        result = result * PRIME + ($artist == null ? 43 : $artist.hashCode());
        final Object $album = this.getAlbum();
        result = result * PRIME + ($album == null ? 43 : $album.hashCode());
        result = result * PRIME + this.getGenre();
        final Object $genreDescription = this.getGenreDescription();
        result = result * PRIME + ($genreDescription == null ? 43 : $genreDescription.hashCode());
        result = result * PRIME + (this.isComplete() ? 79 : 97);
        return result;
    }
}
