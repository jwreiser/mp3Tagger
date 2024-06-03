package com.goodforallcode.mp3Tagger.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RESTUtil {
    public static String callUrl(String method, String urlString, String token) throws IOException {
        URL url = new URL(urlString);
        String json = null;
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestMethod(method);
        if (token != null) {
            connection.setRequestProperty("Authorization", "Bearer " + token);
        } else {
            connection.setDoOutput(true);
            connection.getOutputStream().close();
        }


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

                json = response.toString();
                //                ObjectMapper objectMapper = new ObjectMapper();
                //                musicBrainzCallResults = objectMapper.readValue(json, MusicBrainzCallResults.class);
            }
        }
        return json;
    }
}
