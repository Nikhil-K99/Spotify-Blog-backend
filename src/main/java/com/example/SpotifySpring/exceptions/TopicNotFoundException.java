package com.example.SpotifySpring.exceptions;

public class TopicNotFoundException extends RuntimeException {

    public TopicNotFoundException(String message){
        super(message);
    }

}
