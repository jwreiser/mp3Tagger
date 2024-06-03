package com.goodforallcode.mp3Tagger.model.domain.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifySearchPlaylistResults {
    @JsonProperty("playlists")
    SpotifyPlaylistContainer playlistContainer;

    public SpotifySearchPlaylistResults() {
    }

    public SpotifyPlaylistContainer getPlaylistContainer() {
        return this.playlistContainer;
    }

    @JsonProperty("playlists")
    public void setPlaylistContainer(SpotifyPlaylistContainer playlistContainer) {
        this.playlistContainer = playlistContainer;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SpotifySearchPlaylistResults)) return false;
        final SpotifySearchPlaylistResults other = (SpotifySearchPlaylistResults) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$playlistContainer = this.getPlaylistContainer();
        final Object other$playlistContainer = other.getPlaylistContainer();
        if (this$playlistContainer == null ? other$playlistContainer != null : !this$playlistContainer.equals(other$playlistContainer))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SpotifySearchPlaylistResults;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $playlistContainer = this.getPlaylistContainer();
        result = result * PRIME + ($playlistContainer == null ? 43 : $playlistContainer.hashCode());
        return result;
    }

    public String toString() {
        return "SpotifySearchPlaylistResults(playlistContainer=" + this.getPlaylistContainer() + ")";
    }
}
