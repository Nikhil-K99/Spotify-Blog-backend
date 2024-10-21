package com.example.SpotifySpring.controller;

import com.example.SpotifySpring.dto.TopicDTO;
import com.example.SpotifySpring.service.TopicService;
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
@RequestMapping("api/v1/topics")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class TopicController {

    private final TopicService topicService;

    @GetMapping("/artists")
    public ResponseEntity<List<TopicDTO>> getAllArtists() {
        return status(HttpStatus.OK).body(topicService.getAllArtists());
    }

    @GetMapping("/albums")
    public ResponseEntity<List<TopicDTO>> getAllAlbums() {
        return status(HttpStatus.OK).body(topicService.getAllAlbums());
    }

    @GetMapping("/tracks")
    public ResponseEntity<List<TopicDTO>> getAllTracks() {
        return status(HttpStatus.OK).body(topicService.getAllTracks());
    }

    @GetMapping("/artists/{id}")
    public ResponseEntity<TopicDTO> getArtist(@PathVariable Long id) throws ParseException, SpotifyWebApiException, IOException {
        return status(HttpStatus.OK).body(topicService.getArtistById(id));
    }

    @GetMapping("/albums/{id}")
    public ResponseEntity<TopicDTO> getAlbum(@PathVariable Long id) throws ParseException, SpotifyWebApiException, IOException {
        return status(HttpStatus.OK).body(topicService.getAlbumById(id));
    }

    @GetMapping("/tracks/{id}")
    public ResponseEntity<TopicDTO> getTrack(@PathVariable Long id) throws ParseException, SpotifyWebApiException, IOException {
        return status(HttpStatus.OK).body(topicService.getTrackById(id));
    }



}
