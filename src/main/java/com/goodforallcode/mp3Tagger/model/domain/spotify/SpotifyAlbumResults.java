package com.goodforallcode.mp3Tagger.model.domain.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyAlbumResults {
    @JsonProperty("albums")
    SpotifyAlbums albumsContainer;

    public SpotifyAlbumResults() {
    }

    public SpotifyAlbums getAlbumsContainer() {
        return this.albumsContainer;
    }

    @JsonProperty("albums")
    public void setAlbumsContainer(SpotifyAlbums albumsContainer) {
        this.albumsContainer = albumsContainer;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SpotifyAlbumResults)) return false;
        final SpotifyAlbumResults other = (SpotifyAlbumResults) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$albumsContainer = this.getAlbumsContainer();
        final Object other$albumsContainer = other.getAlbumsContainer();
        if (this$albumsContainer == null ? other$albumsContainer != null : !this$albumsContainer.equals(other$albumsContainer))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SpotifyAlbumResults;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $albumsContainer = this.getAlbumsContainer();
        result = result * PRIME + ($albumsContainer == null ? 43 : $albumsContainer.hashCode());
        return result;
    }

    public String toString() {
        return "SpotifyAlbumResults(albumsContainer=" + this.getAlbumsContainer() + ")";
    }
}
