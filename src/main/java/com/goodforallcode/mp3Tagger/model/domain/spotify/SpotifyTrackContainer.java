
package com.goodforallcode.mp3Tagger.model.domain.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyTrackContainer {

    SpotifyTrack track;

    public SpotifyTrackContainer() {
    }

    public SpotifyTrack getTrack() {
        return this.track;
    }

    public void setTrack(SpotifyTrack track) {
        this.track = track;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SpotifyTrackContainer)) return false;
        final SpotifyTrackContainer other = (SpotifyTrackContainer) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$track = this.getTrack();
        final Object other$track = other.getTrack();
        if (this$track == null ? other$track != null : !this$track.equals(other$track)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SpotifyTrackContainer;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $track = this.getTrack();
        result = result * PRIME + ($track == null ? 43 : $track.hashCode());
        return result;
    }

    public String toString() {
        return "SpotifyTrackContainer(track=" + this.getTrack() + ")";
    }
}
