package com.example.SpotifySpring.service;

import com.example.SpotifySpring.model.User;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

import java.io.IOException;
import java.net.URI;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class SpotifyAPIService {

    private final static String scope = "user-top-read user-read-recently-played user-library-read user-read-email";
    private Instant tokenExpirationTime;

//    @Autowired
    private final SpotifyApi spotifyApi;


    public String logIn(){
        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
                .scope(scope)
                .show_dialog(true)
                .build();

        URI uri = authorizationCodeUriRequest.execute();
        return uri.toString();
    }


    public void getUserCode(String userCode) throws IOException{
        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(userCode).build();

        try{
            AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
            this.tokenExpirationTime = Instant.now().plusSeconds(authorizationCodeCredentials.getExpiresIn());

        } catch ( IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public void checkTokenExpiration(){
        Instant currentTime = Instant.now();

        // If the token is about to expire (less than 60 seconds remaining), refresh it
        if (currentTime.isAfter(tokenExpirationTime.minusSeconds(60))){
            refreshToken();
        }
    }

    public void refreshToken(){
        AuthorizationCodeRefreshRequest authorizationCodeRefreshRequest = spotifyApi.authorizationCodeRefresh()
                .build();

        try {
            AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRefreshRequest.execute();

            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());

            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
            this.tokenExpirationTime = Instant.now().plusSeconds(authorizationCodeCredentials.getExpiresIn());

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public User getCurrentUser() throws IOException, ParseException, SpotifyWebApiException {
        checkTokenExpiration();
        GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = spotifyApi.getCurrentUsersProfile()
                .build();

        se.michaelthelin.spotify.model_objects.specification.User spotifyUser = getCurrentUsersProfileRequest.execute();
        User currentUser = new User();
        currentUser.setUserName(spotifyUser.getDisplayName());
        currentUser.setId(spotifyUser.getId());
        currentUser.setEmail(spotifyUser.getEmail());


        return currentUser;
    }

}


