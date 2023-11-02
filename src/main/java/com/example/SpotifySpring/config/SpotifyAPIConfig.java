package com.example.SpotifySpring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;

import java.net.URI;


@Configuration
public class SpotifyAPIConfig {

    @Value("${spotify.client.id}")
    private String client_id;

    @Value("${spotify.client.secret}")
    private String client_secret;

    private URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/api/v1/auth/get-user-code");

    @Bean
    public SpotifyApi spotifyApi(){
        return new SpotifyApi.Builder()
                .setClientId(client_id)
                .setClientSecret(client_secret)
                .setRedirectUri(redirectUri)
                .build();
    }
}
