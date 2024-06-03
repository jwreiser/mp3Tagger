package com.goodforallcode.mp3Tagger.model.domain.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyPlaylistOwner {

    String id;

    @JsonProperty("display_name")
    String displayName;

    public SpotifyPlaylistOwner() {
    }


    public String getId() {
        return this.id;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("display_name")
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SpotifyPlaylistOwner)) return false;
        final SpotifyPlaylistOwner other = (SpotifyPlaylistOwner) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$displayName = this.getDisplayName();
        final Object other$displayName = other.getDisplayName();
        if (this$displayName == null ? other$displayName != null : !this$displayName.equals(other$displayName))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SpotifyPlaylistOwner;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $displayName = this.getDisplayName();
        result = result * PRIME + ($displayName == null ? 43 : $displayName.hashCode());
        return result;
    }

    public String toString() {
        return "SpotifyPlaylistOwner(id=" + this.getId() + ", displayName=" + this.getDisplayName() + ")";
    }
}
