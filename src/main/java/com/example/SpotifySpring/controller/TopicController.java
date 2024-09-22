package com.example.SpotifySpring.controller;

import com.example.SpotifySpring.dto.AlbumDTO;
import com.example.SpotifySpring.dto.ArtistDTO;
import com.example.SpotifySpring.dto.TrackDTO;
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
    public ResponseEntity<List<ArtistDTO>> getAllArtists() {
        return status(HttpStatus.OK).body(topicService.getAllArtists());
    }

    @GetMapping("/albums")
    public ResponseEntity<List<AlbumDTO>> getAllAlbums() {
        return status(HttpStatus.OK).body(topicService.getAllAlbums());
    }

    @GetMapping("/tracks")
    public ResponseEntity<List<TrackDTO>> getAllTracks() {
        return status(HttpStatus.OK).body(topicService.getAllTracks());
    }

    @GetMapping("/artists/{id}")
    public ResponseEntity<ArtistDTO> getArtist(@PathVariable Long id) throws ParseException, SpotifyWebApiException, IOException {
        return status(HttpStatus.OK).body(topicService.getArtistById(id));
    }

    @GetMapping("/albums/{id}")
    public ResponseEntity<AlbumDTO> getAlbum(@PathVariable Long id) throws ParseException, SpotifyWebApiException, IOException {
        return status(HttpStatus.OK).body(topicService.getAlbumById(id));
    }

    @GetMapping("/tracks/{id}")
    public ResponseEntity<TrackDTO> getTrack(@PathVariable Long id) throws ParseException, SpotifyWebApiException, IOException {
        return status(HttpStatus.OK).body(topicService.getTrackById(id));
    }



}
