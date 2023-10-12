package com.example.SpotifySpring.controller;

import com.example.SpotifySpring.dto.UserDTO;
import com.example.SpotifySpring.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUserbyId(@PathVariable String id) throws IOException, ParseException, SpotifyWebApiException {
        return status(HttpStatus.OK).body(userService.getUserbyId(id));
    }



}