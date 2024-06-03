package com.goodforallcode.mp3Tagger.model.domain.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyTrackInformation {

    String href;

    @JsonProperty("total")
    int numberOfTracks;

    public SpotifyTrackInformation() {
    }

    public String getHref() {
        return this.href;
    }

    public int getNumberOfTracks() {
        return this.numberOfTracks;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @JsonProperty("total")
    public void setNumberOfTracks(int numberOfTracks) {
        this.numberOfTracks = numberOfTracks;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SpotifyTrackInformation)) return false;
        final SpotifyTrackInformation other = (SpotifyTrackInformation) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$href = this.getHref();
        final Object other$href = other.getHref();
        if (this$href == null ? other$href != null : !this$href.equals(other$href)) return false;
        if (this.getNumberOfTracks() != other.getNumberOfTracks()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SpotifyTrackInformation;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $href = this.getHref();
        result = result * PRIME + ($href == null ? 43 : $href.hashCode());
        result = result * PRIME + this.getNumberOfTracks();
        return result;
    }

    public String toString() {
        return "SpotifyTrackInformation(href=" + this.getHref() + ", numberOfTracks=" + this.getNumberOfTracks() + ")";
    }
}
