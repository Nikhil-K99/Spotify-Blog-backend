package com.example.SpotifySpring.controller;

import com.example.SpotifySpring.dto.UserDTO;
import com.example.SpotifySpring.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id) throws IOException, ParseException, SpotifyWebApiException {
        return status(HttpStatus.OK).body(userService.getUserById(id));
    }



}