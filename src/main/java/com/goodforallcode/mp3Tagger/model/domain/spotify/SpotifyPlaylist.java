package com.goodforallcode.mp3Tagger.model.domain.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyPlaylist {

    String id;
    String name;

    String description;
    SpotifyPlaylistOwner owner;


    @JsonProperty("tracks")
    SpotifyTrackInformation trackInformation;

    public SpotifyPlaylist() {
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public SpotifyPlaylistOwner getOwner() {
        return this.owner;
    }

    public SpotifyTrackInformation getTrackInformation() {
        return this.trackInformation;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOwner(SpotifyPlaylistOwner owner) {
        this.owner = owner;
    }

    @JsonProperty("tracks")
    public void setTrackInformation(SpotifyTrackInformation trackInformation) {
        this.trackInformation = trackInformation;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SpotifyPlaylist)) return false;
        final SpotifyPlaylist other = (SpotifyPlaylist) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description))
            return false;
        final Object this$owner = this.getOwner();
        final Object other$owner = other.getOwner();
        if (this$owner == null ? other$owner != null : !this$owner.equals(other$owner)) return false;
        final Object this$trackInformation = this.getTrackInformation();
        final Object other$trackInformation = other.getTrackInformation();
        if (this$trackInformation == null ? other$trackInformation != null : !this$trackInformation.equals(other$trackInformation))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SpotifyPlaylist;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $owner = this.getOwner();
        result = result * PRIME + ($owner == null ? 43 : $owner.hashCode());
        final Object $trackInformation = this.getTrackInformation();
        result = result * PRIME + ($trackInformation == null ? 43 : $trackInformation.hashCode());
        return result;
    }

    public String toString() {
        return "SpotifyPlaylist(id=" + this.getId() + ", name=" + this.getName() + ", description=" + this.getDescription() + ", owner=" + this.getOwner() + ", trackInformation=" + this.getTrackInformation() + ")";
    }
}
