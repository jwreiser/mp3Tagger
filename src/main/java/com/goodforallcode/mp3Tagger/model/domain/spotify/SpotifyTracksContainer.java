package com.goodforallcode.mp3Tagger.model.domain.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyTracksContainer {

    @JsonProperty("items")
    List<SpotifyTrack> tracks;

    public SpotifyTracksContainer() {
    }

    public List<SpotifyTrack> getTracks() {
        return this.tracks;
    }

    @JsonProperty("items")
    public void setTracks(List<SpotifyTrack> tracks) {
        this.tracks = tracks;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SpotifyTracksContainer)) return false;
        final SpotifyTracksContainer other = (SpotifyTracksContainer) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$tracks = this.getTracks();
        final Object other$tracks = other.getTracks();
        if (this$tracks == null ? other$tracks != null : !this$tracks.equals(other$tracks)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SpotifyTracksContainer;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $tracks = this.getTracks();
        result = result * PRIME + ($tracks == null ? 43 : $tracks.hashCode());
        return result;
    }

    public String toString() {
        return "SpotifyTracksContainer(tracks=" + this.getTracks() + ")";
    }
}
