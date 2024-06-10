package com.goodforallcode.mp3Tagger.spotify;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.net.URI;

import static com.goodforallcode.mp3Tagger.spotify.SpotifyRestCaller.clientId;
import static com.goodforallcode.mp3Tagger.spotify.SpotifyRestCaller.secret;

@Getter
@Setter
public class TokenGenerator {
    private static SpotifyRestCaller spotifyCaller = new SpotifyRestCaller();
    private static String token = null;
    private static SpotifyApi spotifyApi;
    private static String playlistId;
    @Value("${file.directory}")
    private String fileLocation;


    boolean disambiguate;
    String userName;

    public TokenGenerator(String userName, boolean disambiguate) {
        this.userName = userName;
        this.disambiguate = disambiguate;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public  SpotifyApi getSpotifyApi() {
        return spotifyApi;
    }



    public String getToken() throws Exception {
        if(token!=null){
            return token;
        }
        //AUth
        //Step 1 get code
        spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(secret)
                .setRedirectUri(new URI("http://localhost:3000"))
                .build();


        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri().scope("playlist-modify-public").build();
        URI execute = authorizationCodeUriRequest.execute();
        String code = "AQCb3bkE4KUNVuS_FwNOfDbmTJVcj-j7CqaCs8cPhnjvOR6rCFxMQqxkDIi0rVt_9pNCJToZFJg86COB5KM7ZkHZVarsgpfLmifROLcZqdmNmULnOd5uwg1SZBBvcnp2DCL-UVMRtPWAvVl4kA9BS-o1v-uH52Zpt0q1SiyCj0Bgn5ZL-W9b8zB7Vz_DxvIHkw";

        System.err.println("Getting tokens");

        //step 2 get tokens
        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();
        AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

        // Set access and refresh token for further "spotifyApi" object usage
        spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
        spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

        token = spotifyCaller.getAccessToken();
        playlistId = spotifyCaller.getPlaylistId(userName, "Latest", token);
        return token;

    }

}
