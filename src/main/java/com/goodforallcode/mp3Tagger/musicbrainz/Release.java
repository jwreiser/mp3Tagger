package com.goodforallcode.mp3Tagger.musicbrainz;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Release {
    int count;
    String title;
    String status;
    @JsonProperty("artist-credit")
    List<ArtistCredit> artistCredits;
    @JsonProperty("track-count")
    int trackCount;

    List<Media> media = new ArrayList<>();

    public Release() {
    }

    public int getCount() {
        return this.count;
    }

    public String getTitle() {
        return this.title;
    }

    public String getStatus() {
        return this.status;
    }

    public List<ArtistCredit> getArtistCredits() {
        return this.artistCredits;
    }

    public int getTrackCount() {
        return this.trackCount;
    }

    public List<Media> getMedia() {
        return this.media;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("artist-credit")
    public void setArtistCredits(List<ArtistCredit> artistCredits) {
        this.artistCredits = artistCredits;
    }

    @JsonProperty("track-count")
    public void setTrackCount(int trackCount) {
        this.trackCount = trackCount;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Release)) return false;
        final Release other = (Release) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getCount() != other.getCount()) return false;
        final Object this$title = this.getTitle();
        final Object other$title = other.getTitle();
        if (this$title == null ? other$title != null : !this$title.equals(other$title)) return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final Object this$artistCredits = this.getArtistCredits();
        final Object other$artistCredits = other.getArtistCredits();
        if (this$artistCredits == null ? other$artistCredits != null : !this$artistCredits.equals(other$artistCredits))
            return false;
        if (this.getTrackCount() != other.getTrackCount()) return false;
        final Object this$media = this.getMedia();
        final Object other$media = other.getMedia();
        if (this$media == null ? other$media != null : !this$media.equals(other$media)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Release;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getCount();
        final Object $title = this.getTitle();
        result = result * PRIME + ($title == null ? 43 : $title.hashCode());
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final Object $artistCredits = this.getArtistCredits();
        result = result * PRIME + ($artistCredits == null ? 43 : $artistCredits.hashCode());
        result = result * PRIME + this.getTrackCount();
        final Object $media = this.getMedia();
        result = result * PRIME + ($media == null ? 43 : $media.hashCode());
        return result;
    }

    public String toString() {
        return "Release(count=" + this.getCount() + ", title=" + this.getTitle() + ", status=" + this.getStatus() + ", artistCredits=" + this.getArtistCredits() + ", trackCount=" + this.getTrackCount() + ", media=" + this.getMedia() + ")";
    }
}
