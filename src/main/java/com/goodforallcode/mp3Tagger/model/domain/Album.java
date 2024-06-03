package com.goodforallcode.mp3Tagger.model.domain;

import com.goodforallcode.mp3Tagger.model.domain.spotify.SpotifyAlbumItem;
import com.goodforallcode.mp3Tagger.model.domain.spotify.SpotifyTrack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Album {
    String artist;
    String name;
    List<File> files;
    SpotifyAlbumItem albumItem;
    List<SpotifyTrack> tracks = new ArrayList<>();

    public Album(String artist, String name, List<File> files) {
        this.artist = artist;
        this.name = name;
        this.files = files;
    }

    public String getArtist() {
        return this.artist;
    }

    public String getName() {
        return this.name;
    }

    public List<File> getFiles() {
        return this.files;
    }

    public SpotifyAlbumItem getAlbumItem() {
        return this.albumItem;
    }

    public List<SpotifyTrack> getTracks() {
        return this.tracks;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public void setAlbumItem(SpotifyAlbumItem albumItem) {
        this.albumItem = albumItem;
    }

    public void setTracks(List<SpotifyTrack> tracks) {
        this.tracks = tracks;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Album)) return false;
        final Album other = (Album) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$artist = this.getArtist();
        final Object other$artist = other.getArtist();
        if (this$artist == null ? other$artist != null : !this$artist.equals(other$artist)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$files = this.getFiles();
        final Object other$files = other.getFiles();
        if (this$files == null ? other$files != null : !this$files.equals(other$files)) return false;
        final Object this$albumItem = this.getAlbumItem();
        final Object other$albumItem = other.getAlbumItem();
        if (this$albumItem == null ? other$albumItem != null : !this$albumItem.equals(other$albumItem)) return false;
        final Object this$tracks = this.getTracks();
        final Object other$tracks = other.getTracks();
        if (this$tracks == null ? other$tracks != null : !this$tracks.equals(other$tracks)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Album;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $artist = this.getArtist();
        result = result * PRIME + ($artist == null ? 43 : $artist.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $files = this.getFiles();
        result = result * PRIME + ($files == null ? 43 : $files.hashCode());
        final Object $albumItem = this.getAlbumItem();
        result = result * PRIME + ($albumItem == null ? 43 : $albumItem.hashCode());
        final Object $tracks = this.getTracks();
        result = result * PRIME + ($tracks == null ? 43 : $tracks.hashCode());
        return result;
    }

    public String toString() {
        return "Album(artist=" + this.getArtist() + ", name=" + this.getName() + ", files=" + this.getFiles() + ", albumItem=" + this.getAlbumItem() + ", tracks=" + this.getTracks() + ")";
    }
}
