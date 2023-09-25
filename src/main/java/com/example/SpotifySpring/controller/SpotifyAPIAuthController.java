package com.example.SpotifySpring.controller;

import com.example.SpotifySpring.service.SpotifyAPIService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static org.springframework.http.ResponseEntity.status;

@CrossOrigin
@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class SpotifyAPIAuthController {

    @Autowired
    private SpotifyAPIService spotifyAPIService;

    @GetMapping("login")
    @ResponseBody
    public ResponseEntity<String> login() {
        return status(HttpStatus.OK).body(spotifyAPIService.logIn());
    }

    @GetMapping("get-user-code")
    public void getSpotifyUserCode(@RequestParam("code") String userCode, HttpServletResponse response) throws IOException {

        try {
            spotifyAPIService.getUserCode(userCode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect("http://localhost:4200/home");

    }

}