package com.goodforallcode.mp3Tagger.model.domain.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyTrackSearchResults {

    @JsonProperty("tracks")
    SpotifyTracksContainer tracksContainer;

    public SpotifyTrackSearchResults() {
    }

    public SpotifyTracksContainer getTracksContainer() {
        return this.tracksContainer;
    }

    @JsonProperty("tracks")
    public void setTracksContainer(SpotifyTracksContainer tracksContainer) {
        this.tracksContainer = tracksContainer;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SpotifyTrackSearchResults)) return false;
        final SpotifyTrackSearchResults other = (SpotifyTrackSearchResults) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$tracksContainer = this.getTracksContainer();
        final Object other$tracksContainer = other.getTracksContainer();
        if (this$tracksContainer == null ? other$tracksContainer != null : !this$tracksContainer.equals(other$tracksContainer))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SpotifyTrackSearchResults;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $tracksContainer = this.getTracksContainer();
        result = result * PRIME + ($tracksContainer == null ? 43 : $tracksContainer.hashCode());
        return result;
    }

    public String toString() {
        return "SpotifyTrackSearchResults(tracksContainer=" + this.getTracksContainer() + ")";
    }
}
