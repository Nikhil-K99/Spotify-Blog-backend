package com.example.SpotifySpring.controller;

import com.example.SpotifySpring.model.User;
import com.example.SpotifySpring.service.SpotifyAPIService;
import com.example.SpotifySpring.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;

import static org.springframework.http.ResponseEntity.status;

@CrossOrigin
@RestController
@RequestMapping("api/v1/auth")
@AllArgsConstructor
public class SpotifyAPIAuthController {

    private final SpotifyAPIService spotifyAPIService;
    private final UserService userService;
    @GetMapping("login")
    @ResponseBody
    public ResponseEntity<String> login() {
        return status(HttpStatus.OK).body(spotifyAPIService.logIn());
    }

    @GetMapping("get-user-code")
    public void getSpotifyUserCode(@RequestParam("code") String userCode, HttpServletResponse response) throws IOException, ParseException, SpotifyWebApiException {

            spotifyAPIService.getUserCode(userCode);
            User user = spotifyAPIService.getCurrentUser();
            userService.saveNewUser(user);

        response.sendRedirect("http://localhost:4200/home");

    }

}