package com.goodforallcode.mp3Tagger.musicbrainz;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.goodforallcode.mp3Tagger.model.domain.Mp3Info;
import com.mpatric.mp3agic.ID3v1Genres;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public class MusicBrainzCallResults {
    static LevenshteinDistance distanceCalculator = LevenshteinDistance.getDefaultInstance();
    int count;
    List<Recording> recordings;

    public MusicBrainzCallResults() {
        String test;
    }

    public Mp3Info getMp3InfoFromResults() {
        Mp3Info results = null;
        if (recordings.size() > 0) {
            Recording recordingToUse = recordings.get(0);
            Release releaseToUse = null;
            if (recordingToUse.getReleases().size() > 0) {
                releaseToUse = recordingToUse.getReleases().get(0);
            }
            results = getMp3Info(recordingToUse, releaseToUse);
        }
        return results;
    }

    private static Mp3Info getMp3Info(Recording recordingToUse, Release releaseToUse) {
        Mp3Info results;
        String title = recordingToUse.getTitle();
        String topGenreDescription = null;
        int topCount = 1;
        int topGenre = 0, currentGenre;
        if (recordingToUse.getTags() != null) {
            for (Tag tag : recordingToUse.getTags()) {
                if (tag.getCount() > topCount) {
                    currentGenre = ID3v1Genres.matchGenreDescription(tag.getName());
                    if (currentGenre > 0) {
                        topGenreDescription = tag.getName();
                        topCount = tag.getCount();
                        topGenre = currentGenre;
                    }
                }
            }
        }
        String artist = null;
        if (recordingToUse.getArtistCredits().size() > 0) {
            artist = recordingToUse.getArtistCredits().get(0).getName();
        }
        String album = null;
        if (releaseToUse != null) {
            album = releaseToUse.getTitle();
            //trust these over recording artists
            if (releaseToUse.getArtistCredits() != null && releaseToUse.getArtistCredits().size() > 0) {
                artist = releaseToUse.getArtistCredits().get(0).getName();
            }
        }
        if (topGenreDescription != null) {
            results = new Mp3Info(title, artist, album, topGenre, topGenreDescription);
        } else {
            results = new Mp3Info(title, artist, album);
        }
        return results;
    }

    public Mp3Info getMp3InfoFromResults(String suggestedTrack, Integer trackNumber, Integer numberOfTracks, long duration) {
        Results results = getElementsToUse(suggestedTrack, trackNumber, numberOfTracks, duration, false);

        if (results.getRecordingToUse() == null || results.getReleaseToUse() == null) {
            results = getElementsToUse(suggestedTrack, trackNumber, numberOfTracks, duration, true);
            if (results.getRecordingToUse() == null || results.getReleaseToUse() == null) {
                return null;
            } else {
                return getMp3Info(results.getRecordingToUse(), results.getReleaseToUse());
            }
        } else {
            return getMp3Info(results.getRecordingToUse(), results.getReleaseToUse());
        }
    }

    public int getCount() {
        return this.count;
    }

    public List<Recording> getRecordings() {
        return this.recordings;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setRecordings(List<Recording> recordings) {
        this.recordings = recordings;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof MusicBrainzCallResults)) return false;
        final MusicBrainzCallResults other = (MusicBrainzCallResults) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getCount() != other.getCount()) return false;
        final Object this$recordings = this.getRecordings();
        final Object other$recordings = other.getRecordings();
        if (this$recordings == null ? other$recordings != null : !this$recordings.equals(other$recordings))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof MusicBrainzCallResults;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getCount();
        final Object $recordings = this.getRecordings();
        result = result * PRIME + ($recordings == null ? 43 : $recordings.hashCode());
        return result;
    }

    public String toString() {
        return "MusicBrainzCallResults(count=" + this.getCount() + ", recordings=" + this.getRecordings() + ")";
    }

    private class Results {
        Recording recordingToUse = null;
        Release releaseToUse = null;

        public Results(Recording recordingToUse, Release releaseToUse) {
            this.recordingToUse = recordingToUse;
            this.releaseToUse = releaseToUse;
        }

        public Recording getRecordingToUse() {
            return this.recordingToUse;
        }

        public Release getReleaseToUse() {
            return this.releaseToUse;
        }

        public void setRecordingToUse(Recording recordingToUse) {
            this.recordingToUse = recordingToUse;
        }

        public void setReleaseToUse(Release releaseToUse) {
            this.releaseToUse = releaseToUse;
        }

        public boolean equals(final Object o) {
            if (o == this) return true;
            if (!(o instanceof Results)) return false;
            final Results other = (Results) o;
            if (!other.canEqual((Object) this)) return false;
            final Object this$recordingToUse = this.getRecordingToUse();
            final Object other$recordingToUse = other.getRecordingToUse();
            if (this$recordingToUse == null ? other$recordingToUse != null : !this$recordingToUse.equals(other$recordingToUse))
                return false;
            final Object this$releaseToUse = this.getReleaseToUse();
            final Object other$releaseToUse = other.getReleaseToUse();
            if (this$releaseToUse == null ? other$releaseToUse != null : !this$releaseToUse.equals(other$releaseToUse))
                return false;
            return true;
        }

        protected boolean canEqual(final Object other) {
            return other instanceof Results;
        }

        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            final Object $recordingToUse = this.getRecordingToUse();
            result = result * PRIME + ($recordingToUse == null ? 43 : $recordingToUse.hashCode());
            final Object $releaseToUse = this.getReleaseToUse();
            result = result * PRIME + ($releaseToUse == null ? 43 : $releaseToUse.hashCode());
            return result;
        }

        public String toString() {
            return "MusicBrainzCallResults.Results(recordingToUse=" + this.getRecordingToUse() + ", releaseToUse=" + this.getReleaseToUse() + ")";
        }
    }

    private Results getElementsToUse(String suggestedTrack, Integer trackNumber, Integer numberOfTracks, long duration, boolean assumeTrackCountIncorrect) {
        Recording recordingToUse = null;
        Release releaseToUse = null;
        long bestDifference = Integer.MAX_VALUE;
        long currentDuration, currentDifference;

        boolean bestTitleIsASubset = false;
        int bestDistance = Integer.MAX_VALUE, currentDistance;
        String currentTrack;
        suggestedTrack = suggestedTrack.replace(trackNumber + "", "").replace("&", "and").trim().toLowerCase();

        if (recordings.size() > 0) {
            for (Recording recording : recordings) {
                if (!suggestedTrack.isEmpty()) {
                    currentTrack = recording.getTitle().replace("&", "and").toLowerCase();
                    if (suggestedTrack.contains(currentTrack)) {
                        currentDistance = distanceCalculator.apply(suggestedTrack, currentTrack);
                        if (bestTitleIsASubset) {
                            if (currentDistance > bestDistance) {
                                continue;
                            }
                        }
                        bestTitleIsASubset = true;
                        bestDistance = currentDistance;
                        recordingToUse = recording;//at least use this if we can't find a release
                    } else {
                        if (bestTitleIsASubset) {
                            continue;
                        } else {
                            currentDistance = distanceCalculator.apply(suggestedTrack, currentTrack);
                            if (currentDistance > bestDistance) {
                                continue;
                            } else {
                                bestDistance = currentDistance;
                            }
                        }
                    }
                }

                if (recording.getReleases() != null) {
                    for (Release release : recording.getReleases()) {
                        if ((release.getTrackCount() == numberOfTracks || (assumeTrackCountIncorrect && release.getTrackCount() > numberOfTracks)) && mediaIsRightTrack(release, trackNumber)) {

                            //TODO see if possible to get more than one back
                            currentDuration = release.getMedia().get(0).getTrack().get(0).getLength();
                            if (releaseToUse != null) {
                                currentDifference = Math.abs(duration - currentDuration);
                                if (currentDifference < bestDifference) {
                                    releaseToUse = release;
                                    bestDifference = Math.abs(duration - currentDuration);
                                    recordingToUse = recording;
                                }
                            } else {
                                releaseToUse = release;
                                bestDifference = Math.abs(duration - currentDuration);
                                recordingToUse = recording;
                            }
                        }
                    }
                }
            }
        }
        return new Results(recordingToUse, releaseToUse);
    }

    private boolean mediaIsRightTrack(Release release, Integer trackNumber) {
        boolean hasMedia = release.getMedia() != null && release.getMedia().size() == 1;
        if (hasMedia) {
            Media media = release.getMedia().get(0);
            //TODO replace the CD assumption with input
            if (media.getFormat() != null && media.getFormat().toUpperCase().equals("CD") && media.getTrackOffset() == (trackNumber - 1)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }
}
