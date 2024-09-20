package com.example.SpotifySpring.controller;


import com.example.SpotifySpring.dto.PostRequestDTO;
import com.example.SpotifySpring.dto.PostResponseDTO;
import com.example.SpotifySpring.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("api/v1/posts")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class PostController {

    private final PostService postService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequestDTO postRequestDTO) throws IOException, ParseException, SpotifyWebApiException {
        postService.save(postRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> getPost(@PathVariable Long id) throws IOException, ParseException, SpotifyWebApiException {
        return status(HttpStatus.OK).body(postService.getPost(id));
    }

    @GetMapping
    public  ResponseEntity<List<PostResponseDTO>> getAllPosts() {
        return status(HttpStatus.OK).body(postService.getAllPosts());
    }


    @GetMapping("/by-user/{username}")
    public ResponseEntity<List<PostResponseDTO>> getPostsByUsername(@PathVariable String username) {
        return status(HttpStatus.OK).body(postService.getPostsByUsername(username));
    }

    @GetMapping("/by-topic/{topicId}")
    public ResponseEntity<List<PostResponseDTO>> getPostsByTopic(@PathVariable Long topicId) {
        return status(HttpStatus.OK).body(postService.getPostsByTopic(topicId));
    }


}
