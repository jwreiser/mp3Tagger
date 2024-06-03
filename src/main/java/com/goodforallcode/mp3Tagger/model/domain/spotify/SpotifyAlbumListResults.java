package com.goodforallcode.mp3Tagger.model.domain.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyAlbumListResults {
    @JsonProperty("albums")
    List<SpotifyAlbumItem> albums;

    public SpotifyAlbumListResults() {
    }

    public List<SpotifyAlbumItem> getAlbums() {
        return this.albums;
    }

    @JsonProperty("albums")
    public void setAlbums(List<SpotifyAlbumItem> albums) {
        this.albums = albums;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SpotifyAlbumListResults)) return false;
        final SpotifyAlbumListResults other = (SpotifyAlbumListResults) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$albums = this.getAlbums();
        final Object other$albums = other.getAlbums();
        if (this$albums == null ? other$albums != null : !this$albums.equals(other$albums)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SpotifyAlbumListResults;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $albums = this.getAlbums();
        result = result * PRIME + ($albums == null ? 43 : $albums.hashCode());
        return result;
    }

    public String toString() {
        return "SpotifyAlbumListResults(albums=" + this.getAlbums() + ")";
    }
}
