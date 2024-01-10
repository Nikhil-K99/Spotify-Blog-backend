package com.example.SpotifySpring.controller;

import com.example.SpotifySpring.dto.TopicSearchDTO;
import com.example.SpotifySpring.service.TopicSearchService;
import lombok.AllArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/search")
@CrossOrigin(origins = "http://localhost:4200")
public class TopicSearchController {

    private final TopicSearchService topicSearchService;

    @GetMapping("/artist/{artistQuery}")
    public ResponseEntity<List<TopicSearchDTO>> getSearchedArtist(@PathVariable String artistQuery) throws IOException, ParseException, SpotifyWebApiException {
        return status(HttpStatus.OK).body(topicSearchService.getSearchedArtist(artistQuery));
    }

    @GetMapping("/album/{albumQuery}")
    public ResponseEntity<List<TopicSearchDTO>> getSearchedAlbum(@PathVariable String albumQuery) throws IOException, ParseException, SpotifyWebApiException {
        return status(HttpStatus.OK).body(topicSearchService.getSearchedAlbum(albumQuery));
    }

    @GetMapping("/track/{trackQuery}")
    public ResponseEntity<List<TopicSearchDTO>> getSearchedTrack(@PathVariable String trackQuery) throws IOException, ParseException, SpotifyWebApiException {
        return status(HttpStatus.OK).body(topicSearchService.getSearchedTrack(trackQuery));
    }

}
