package com.goodforallcode.mp3Tagger.musicbrainz;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Track {
    int length;

    public Track() {
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Track)) return false;
        final Track other = (Track) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getLength() != other.getLength()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Track;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getLength();
        return result;
    }

    public String toString() {
        return "Track(length=" + this.getLength() + ")";
    }
}
