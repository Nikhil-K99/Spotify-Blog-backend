package com.example.SpotifySpring.exceptions;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(String message){
        super(message);
    }
}
