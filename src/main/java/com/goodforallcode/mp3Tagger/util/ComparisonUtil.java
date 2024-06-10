package com.goodforallcode.mp3Tagger.util;

import org.apache.commons.text.similarity.LevenshteinDistance;

public class ComparisonUtil {
    private static LevenshteinDistance distanceCalculator = LevenshteinDistance.getDefaultInstance();
    private static int ALBUM_NAME_DISTANCE_CUTOFF_STARTS_WITH = 7;
    private static int ALBUM_NAME_DISTANCE_CUTOFF_CONTAINS = 7;
    private static int ALBUM_NAME_DISTANCE_CUTOFF_NO_CONTAINS = 5;
    private static int ARTIST_NAME_DISTANCE_CUTOFF_STARTS_WITH = 12;
    private static int ARTIST_NAME_DISTANCE_CUTOFF_CONTAINS = 5;
    private static int ARTIST_NAME_DISTANCE_CUTOFF_NO_CONTAINS = 3;
    private static int TRACK_NAME_DISTANCE_CUTOFF_CONTAINS = 10;
    private static int TRACK_NAME_DISTANCE_CUTOFF_NO_CONTAINS = 7;
    private static int TRACK_NAME_DISTANCE_CUTOFF_STARTS_WITH = 25;

    public static long  artistsAreEqual(String spotifyArtist, String targetArtist) {
        long match = -1;
        long currentDistance = distanceCalculator.apply(spotifyArtist, targetArtist);
        if (spotifyArtist.equals(targetArtist)) {
            match = 0;
        } else if (targetArtist.contains("various")) {
            match = 0;
        } else if (currentDistance < ARTIST_NAME_DISTANCE_CUTOFF_STARTS_WITH && (spotifyArtist.startsWith(targetArtist) || targetArtist.startsWith(spotifyArtist))){
            match = currentDistance;
        }else if (currentDistance < ARTIST_NAME_DISTANCE_CUTOFF_CONTAINS && (spotifyArtist.contains(targetArtist) || targetArtist.contains(spotifyArtist))){
            match = currentDistance;
        }else if (currentDistance < ARTIST_NAME_DISTANCE_CUTOFF_NO_CONTAINS){
            match = currentDistance;
        }
        return match;
    }

    public static long  tracksAreEqual(String spotifyTrack, String targetTrack) {
        long match = -1;
        long currentDistance = distanceCalculator.apply(spotifyTrack, targetTrack);
        if (spotifyTrack.equals(targetTrack)) {
            match = 0;
        }else if (currentDistance < TRACK_NAME_DISTANCE_CUTOFF_STARTS_WITH && (spotifyTrack.startsWith(targetTrack) || targetTrack.startsWith(spotifyTrack))){
            match = currentDistance;
        }else if (currentDistance < TRACK_NAME_DISTANCE_CUTOFF_CONTAINS && (spotifyTrack.contains(targetTrack) || targetTrack.contains(spotifyTrack))){
            match = currentDistance;
        }else if (currentDistance < TRACK_NAME_DISTANCE_CUTOFF_NO_CONTAINS){
            match = currentDistance;
        }
        return match;
    }
    public static long albumsAreEqual(String spotifyAlbum, String targetAlbum) {
        long match = -1;
        long currentDistance = distanceCalculator.apply(spotifyAlbum, targetAlbum);
        if (spotifyAlbum.equals(targetAlbum)) {
            match = 0;
        }else if (targetAlbum.length() > 5 && spotifyAlbum.length() > 5 && currentDistance<ARTIST_NAME_DISTANCE_CUTOFF_STARTS_WITH && (targetAlbum.startsWith(spotifyAlbum) || spotifyAlbum.startsWith(targetAlbum))) {
            match = 0;
        }else if (targetAlbum.length() > 5 && spotifyAlbum.length() > 5 && currentDistance<ARTIST_NAME_DISTANCE_CUTOFF_CONTAINS && (targetAlbum.contains(spotifyAlbum) || spotifyAlbum.contains(targetAlbum))) {
            match = 0;
        }else if (currentDistance < ALBUM_NAME_DISTANCE_CUTOFF_NO_CONTAINS) {
            match = currentDistance;
        }
        return match;
    }

}
