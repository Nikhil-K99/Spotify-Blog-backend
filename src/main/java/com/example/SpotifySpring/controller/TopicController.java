package com.example.SpotifySpring.controller;

import com.example.SpotifySpring.dto.AlbumDTO;
import com.example.SpotifySpring.dto.ArtistDTO;
import com.example.SpotifySpring.dto.TrackDTO;
import com.example.SpotifySpring.service.TopicService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("api/v1/topics")
@AllArgsConstructor
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


}
