package com.goodforallcode.mp3Tagger.model.domain.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyPlaylistTracksResults {
    @JsonProperty("items")
    List<SpotifyTrackContainer> trackContainers;

    public SpotifyPlaylistTracksResults() {
    }

    public List<SpotifyTrackContainer> getTrackContainers() {
        return this.trackContainers;
    }

    @JsonProperty("items")
    public void setTrackContainers(List<SpotifyTrackContainer> trackContainers) {
        this.trackContainers = trackContainers;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SpotifyPlaylistTracksResults)) return false;
        final SpotifyPlaylistTracksResults other = (SpotifyPlaylistTracksResults) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$trackContainers = this.getTrackContainers();
        final Object other$trackContainers = other.getTrackContainers();
        if (this$trackContainers == null ? other$trackContainers != null : !this$trackContainers.equals(other$trackContainers))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SpotifyPlaylistTracksResults;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $trackContainers = this.getTrackContainers();
        result = result * PRIME + ($trackContainers == null ? 43 : $trackContainers.hashCode());
        return result;
    }

    public String toString() {
        return "SpotifyPlaylistTracksResults(trackContainers=" + this.getTrackContainers() + ")";
    }
}
