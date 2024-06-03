package com.goodforallcode.mp3Tagger.model.domain.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyPlaylistContainer {

    @JsonProperty("items")
    List<SpotifyPlaylist> playlists;

    public SpotifyPlaylistContainer() {
    }

    public List<SpotifyPlaylist> getPlaylists() {
        return this.playlists;
    }

    @JsonProperty("items")
    public void setPlaylists(List<SpotifyPlaylist> playlists) {
        this.playlists = playlists;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SpotifyPlaylistContainer)) return false;
        final SpotifyPlaylistContainer other = (SpotifyPlaylistContainer) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$playlists = this.getPlaylists();
        final Object other$playlists = other.getPlaylists();
        if (this$playlists == null ? other$playlists != null : !this$playlists.equals(other$playlists)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SpotifyPlaylistContainer;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $playlists = this.getPlaylists();
        result = result * PRIME + ($playlists == null ? 43 : $playlists.hashCode());
        return result;
    }

    public String toString() {
        return "SpotifyPlaylistContainer(playlists=" + this.getPlaylists() + ")";
    }
}
