package com.goodforallcode.mp3Tagger.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goodforallcode.mp3Tagger.musicbrainz.MusicBrainzCallResults;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


public class MusicBrainzRestCaller {

    public MusicBrainzCallResults getInfoFromFullInformation(String trackName, String album, String artist, String email) {
        MusicBrainzCallResults musicBrainzCallResults = null;

        try {
            String urlString = "https://musicbrainz.org/ws/2/recording?query=artist:" +URLEncoder.encode("\""+artist+"\"", StandardCharsets.UTF_8.toString())
                    + "%20AND%20recording:" + URLEncoder.encode("\""+trackName+"\"", StandardCharsets.UTF_8.toString())
                    + "%20AND%20release:" + URLEncoder.encode("\""+album+"\"", StandardCharsets.UTF_8.toString()) ;

            musicBrainzCallResults = callUrl(email, musicBrainzCallResults, urlString);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return musicBrainzCallResults;
    }
    public MusicBrainzCallResults getInfoFromArtistAndAlbum(String artist, String album, String email) {
        MusicBrainzCallResults musicBrainzCallResults = null;

        try {
            String urlString = "https://musicbrainz.org/ws/2/recording?query=artist:" + URLEncoder.encode("\""+artist+"\"", StandardCharsets.UTF_8.toString())
                    + "%20AND%20release:" + URLEncoder.encode("\""+album+"\"", StandardCharsets.UTF_8.toString()) ;

            musicBrainzCallResults = callUrl(email, musicBrainzCallResults, urlString);

        }   catch(IOException e) {
            throw new RuntimeException(e);
        }catch(Exception ex) {
            ex.printStackTrace();
        }


        return musicBrainzCallResults;
    }
    public MusicBrainzCallResults getInfoFromAlbum(String trackName, String album, String email) {
        MusicBrainzCallResults musicBrainzCallResults = null;

        try {
            String urlString = "https://musicbrainz.org/ws/2/recording?query=recording:" + URLEncoder.encode("\""+trackName+"\"", StandardCharsets.UTF_8.toString())
                    + "%20AND%20release:" + URLEncoder.encode("\""+album+"\"", StandardCharsets.UTF_8.toString()) ;

            musicBrainzCallResults = callUrl(email, musicBrainzCallResults, urlString);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return musicBrainzCallResults;
    }
    public MusicBrainzCallResults getInfoFromArtistAndTrack(String trackName, String artist, String email) {
        MusicBrainzCallResults musicBrainzCallResults = null;

        try {
            String urlString = "https://musicbrainz.org/ws/2/recording?query=recording:" + URLEncoder.encode("\""+trackName+"\"", StandardCharsets.UTF_8.toString())
                    + "%20AND%20artist:" + URLEncoder.encode("\""+artist+"\"", StandardCharsets.UTF_8.toString()) ;

            musicBrainzCallResults = callUrl(email, musicBrainzCallResults, urlString);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return musicBrainzCallResults;
    }
    public MusicBrainzCallResults getInfoFromTrack(String trackName,  String email) {
        MusicBrainzCallResults musicBrainzCallResults = null;

        try {
            String urlString = "https://musicbrainz.org/ws/2/recording?query=recording:" + URLEncoder.encode("\""+trackName+"\"", StandardCharsets.UTF_8.toString());

            musicBrainzCallResults = callUrl(email, musicBrainzCallResults, urlString);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return musicBrainzCallResults;
    }

    private static MusicBrainzCallResults callUrl(String email, MusicBrainzCallResults musicBrainzCallResults, String urlString) throws IOException {
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("PlaylistGenerator/1.0.0", email);
        connection.setRequestMethod("GET");

        // Collect the response code
        int responseCode = connection.getResponseCode();

        if (responseCode == connection.HTTP_OK) {
            // Create a reader with the input stream reader.
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));) {

                String inputLine;

                // Create a string buffer
                StringBuffer response = new StringBuffer();

                // Write each of the input line
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                String json = response.toString();
                ObjectMapper objectMapper = new ObjectMapper();
                musicBrainzCallResults = objectMapper.readValue(json, MusicBrainzCallResults.class);
            }
        }
        return musicBrainzCallResults;
    }


}
