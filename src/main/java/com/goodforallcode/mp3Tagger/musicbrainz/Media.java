package com.goodforallcode.mp3Tagger.musicbrainz;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Media {
    @JsonProperty("track-count")
    int trackCount;
    @JsonProperty("track-offset")
    int trackOffset;
    String format;

    List<Track> track = new ArrayList<>();

    public Media() {
    }

    public int getTrackCount() {
        return this.trackCount;
    }

    public int getTrackOffset() {
        return this.trackOffset;
    }

    public String getFormat() {
        return this.format;
    }

    public List<Track> getTrack() {
        return this.track;
    }

    @JsonProperty("track-count")
    public void setTrackCount(int trackCount) {
        this.trackCount = trackCount;
    }

    @JsonProperty("track-offset")
    public void setTrackOffset(int trackOffset) {
        this.trackOffset = trackOffset;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setTrack(List<Track> track) {
        this.track = track;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Media)) return false;
        final Media other = (Media) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getTrackCount() != other.getTrackCount()) return false;
        if (this.getTrackOffset() != other.getTrackOffset()) return false;
        final Object this$format = this.getFormat();
        final Object other$format = other.getFormat();
        if (this$format == null ? other$format != null : !this$format.equals(other$format)) return false;
        final Object this$track = this.getTrack();
        final Object other$track = other.getTrack();
        if (this$track == null ? other$track != null : !this$track.equals(other$track)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Media;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getTrackCount();
        result = result * PRIME + this.getTrackOffset();
        final Object $format = this.getFormat();
        result = result * PRIME + ($format == null ? 43 : $format.hashCode());
        final Object $track = this.getTrack();
        result = result * PRIME + ($track == null ? 43 : $track.hashCode());
        return result;
    }

    public String toString() {
        return "Media(trackCount=" + this.getTrackCount() + ", trackOffset=" + this.getTrackOffset() + ", format=" + this.getFormat() + ", track=" + this.getTrack() + ")";
    }
}
