package com.goodforallcode.mp3Tagger.musicbrainz;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Recording {
    private int score;
    private String title;
    private int length;
    @JsonProperty ("artist-credit")
    private
    List<ArtistCredit> artistCredits;
    private String disambiguation;
    private List<Release> releases;
    private List<Tag> tags;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<ArtistCredit> getArtistCredits() {
        return artistCredits;
    }

    public void setArtistCredits(List<ArtistCredit> artistCredits) {
        this.artistCredits = artistCredits;
    }

    public String getDisambiguation() {
        return disambiguation;
    }

    public void setDisambiguation(String disambiguation) {
        this.disambiguation = disambiguation;
    }

    public List<Release> getReleases() {
        return releases;
    }

    public void setReleases(List<Release> releases) {
        this.releases = releases;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
