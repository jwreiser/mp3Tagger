package com.goodforallcode.mp3Tagger.model.domain.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyAlbumItem {

    @JsonProperty("album_type")
    String albumType;
    List<SpotifyArtist> artists;
    String id;
    String name;
    int popularity;
    @JsonProperty("total_tracks")
    int totalTracks;

    @JsonProperty("tracks")
    SpotifyTracksContainer tracksContainer;

    public SpotifyAlbumItem() {
    }

    public String getAlbumType() {
        return this.albumType;
    }

    public List<SpotifyArtist> getArtists() {
        return this.artists;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getPopularity() {
        return this.popularity;
    }

    public int getTotalTracks() {
        return this.totalTracks;
    }

    public SpotifyTracksContainer getTracksContainer() {
        return this.tracksContainer;
    }

    @JsonProperty("album_type")
    public void setAlbumType(String albumType) {
        this.albumType = albumType;
    }

    public void setArtists(List<SpotifyArtist> artists) {
        this.artists = artists;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    @JsonProperty("total_tracks")
    public void setTotalTracks(int totalTracks) {
        this.totalTracks = totalTracks;
    }

    @JsonProperty("tracks")
    public void setTracksContainer(SpotifyTracksContainer tracksContainer) {
        this.tracksContainer = tracksContainer;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SpotifyAlbumItem)) return false;
        final SpotifyAlbumItem other = (SpotifyAlbumItem) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$albumType = this.getAlbumType();
        final Object other$albumType = other.getAlbumType();
        if (this$albumType == null ? other$albumType != null : !this$albumType.equals(other$albumType)) return false;
        final Object this$artists = this.getArtists();
        final Object other$artists = other.getArtists();
        if (this$artists == null ? other$artists != null : !this$artists.equals(other$artists)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        if (this.getPopularity() != other.getPopularity()) return false;
        if (this.getTotalTracks() != other.getTotalTracks()) return false;
        final Object this$tracksContainer = this.getTracksContainer();
        final Object other$tracksContainer = other.getTracksContainer();
        if (this$tracksContainer == null ? other$tracksContainer != null : !this$tracksContainer.equals(other$tracksContainer))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SpotifyAlbumItem;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $albumType = this.getAlbumType();
        result = result * PRIME + ($albumType == null ? 43 : $albumType.hashCode());
        final Object $artists = this.getArtists();
        result = result * PRIME + ($artists == null ? 43 : $artists.hashCode());
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        result = result * PRIME + this.getPopularity();
        result = result * PRIME + this.getTotalTracks();
        final Object $tracksContainer = this.getTracksContainer();
        result = result * PRIME + ($tracksContainer == null ? 43 : $tracksContainer.hashCode());
        return result;
    }

    public String toString() {
        return "SpotifyAlbumItem(albumType=" + this.getAlbumType() + ", artists=" + this.getArtists() + ", id=" + this.getId() + ", name=" + this.getName() + ", popularity=" + this.getPopularity() + ", totalTracks=" + this.getTotalTracks() + ", tracksContainer=" + this.getTracksContainer() + ")";
    }
}
