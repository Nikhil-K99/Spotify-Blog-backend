package com.example.SpotifySpring.exceptions;

public class VoteAlreadyExistsException extends RuntimeException {
    public VoteAlreadyExistsException(String exMessage) {
        super(exMessage);
    }
}
