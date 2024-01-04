package com.example.SpotifySpring.controller;

import com.example.SpotifySpring.dto.TopicSearchDTO;
import com.example.SpotifySpring.service.TopicSearchService;
import lombok.AllArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/search")
public class TopicSearchController {

    private final TopicSearchService topicSearchService;

    @GetMapping("/artist")
    public ResponseEntity<List<TopicSearchDTO>> getSearchedArtist(String artistQuery) throws IOException, ParseException, SpotifyWebApiException {
        return status(HttpStatus.OK).body(topicSearchService.getSearchedArtist(artistQuery));
    }

    @GetMapping("/album")
    public ResponseEntity<List<TopicSearchDTO>> getSearchedAlbum(String albumQuery) throws IOException, ParseException, SpotifyWebApiException {
        return status(HttpStatus.OK).body(topicSearchService.getSearchedAlbum(albumQuery));
    }

    @GetMapping("/track")
    public ResponseEntity<List<TopicSearchDTO>> getSearchedTrack(String trackQuery) throws IOException, ParseException, SpotifyWebApiException {
        return status(HttpStatus.OK).body(topicSearchService.getSearchedTrack(trackQuery));
    }

}
