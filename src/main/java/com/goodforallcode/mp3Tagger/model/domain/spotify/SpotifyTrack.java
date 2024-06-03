package com.goodforallcode.mp3Tagger.model.domain.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyTrack {

    String name;
    String id;

    @JsonProperty("duration_ms")
    int duration;

    @JsonProperty("track_number")
    int trackNumber;

    SpotifyAlbumItem album;
    int popularity;

    @JsonProperty("is_local")
    boolean local;
    @JsonProperty("track")
    boolean track;

    @JsonProperty("href")
    String url;

    List<SpotifyArtist> artists;

    public SpotifyTrack() {
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.id;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getTrackNumber() {
        return this.trackNumber;
    }

    public SpotifyAlbumItem getAlbum() {
        return this.album;
    }

    public int getPopularity() {
        return this.popularity;
    }

    public boolean isLocal() {
        return this.local;
    }

    public boolean isTrack() {
        return this.track;
    }

    public String getUrl() {
        return this.url;
    }

    public List<SpotifyArtist> getArtists() {
        return this.artists;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("duration_ms")
    public void setDuration(int duration) {
        this.duration = duration;
    }

    @JsonProperty("track_number")
    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public void setAlbum(SpotifyAlbumItem album) {
        this.album = album;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    @JsonProperty("is_local")
    public void setLocal(boolean local) {
        this.local = local;
    }

    @JsonProperty("track")
    public void setTrack(boolean track) {
        this.track = track;
    }

    @JsonProperty("href")
    public void setUrl(String url) {
        this.url = url;
    }

    public void setArtists(List<SpotifyArtist> artists) {
        this.artists = artists;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SpotifyTrack)) return false;
        final SpotifyTrack other = (SpotifyTrack) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        if (this.getDuration() != other.getDuration()) return false;
        if (this.getTrackNumber() != other.getTrackNumber()) return false;
        final Object this$album = this.getAlbum();
        final Object other$album = other.getAlbum();
        if (this$album == null ? other$album != null : !this$album.equals(other$album)) return false;
        if (this.getPopularity() != other.getPopularity()) return false;
        if (this.isLocal() != other.isLocal()) return false;
        if (this.isTrack() != other.isTrack()) return false;
        final Object this$url = this.getUrl();
        final Object other$url = other.getUrl();
        if (this$url == null ? other$url != null : !this$url.equals(other$url)) return false;
        final Object this$artists = this.getArtists();
        final Object other$artists = other.getArtists();
        if (this$artists == null ? other$artists != null : !this$artists.equals(other$artists)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SpotifyTrack;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        result = result * PRIME + this.getDuration();
        result = result * PRIME + this.getTrackNumber();
        final Object $album = this.getAlbum();
        result = result * PRIME + ($album == null ? 43 : $album.hashCode());
        result = result * PRIME + this.getPopularity();
        result = result * PRIME + (this.isLocal() ? 79 : 97);
        result = result * PRIME + (this.isTrack() ? 79 : 97);
        final Object $url = this.getUrl();
        result = result * PRIME + ($url == null ? 43 : $url.hashCode());
        final Object $artists = this.getArtists();
        result = result * PRIME + ($artists == null ? 43 : $artists.hashCode());
        return result;
    }

    public String toString() {
        return "SpotifyTrack(name=" + this.getName() + ", id=" + this.getId() + ", duration=" + this.getDuration() + ", trackNumber=" + this.getTrackNumber() + ", album=" + this.getAlbum() + ", popularity=" + this.getPopularity() + ", local=" + this.isLocal() + ", track=" + this.isTrack() + ", url=" + this.getUrl() + ", artists=" + this.getArtists() + ")";
    }
}
