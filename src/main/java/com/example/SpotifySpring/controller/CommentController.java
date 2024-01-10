package com.example.SpotifySpring.controller;


import com.example.SpotifySpring.dto.CommentDTO;
import com.example.SpotifySpring.service.CommentService;
import lombok.AllArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("api/v1/comments")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentDTO commentDTO) throws IOException, ParseException, SpotifyWebApiException {
        commentService.save(commentDTO);
        return new ResponseEntity<>(CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDTO>> getAllCommentsForPost(@PathVariable Long postId) {
        return status(HttpStatus.OK).body(commentService.getAllCommentsForPost(postId));
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<CommentDTO>> getAllCommentsForUser(@PathVariable String username) {
        return status(HttpStatus.OK).body(commentService.getAllCommentsForUser(username));
    }


}
