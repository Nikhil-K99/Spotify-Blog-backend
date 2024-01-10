package com.example.SpotifySpring.controller;

import com.example.SpotifySpring.dto.VoteDTO;
import com.example.SpotifySpring.service.VoteService;
import lombok.AllArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/votes")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<Void> vote(@RequestBody VoteDTO voteDTO) throws IOException, ParseException, SpotifyWebApiException {
        voteService.vote(voteDTO);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
