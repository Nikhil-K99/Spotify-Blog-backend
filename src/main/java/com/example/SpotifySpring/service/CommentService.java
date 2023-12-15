package com.example.SpotifySpring.service;

import com.example.SpotifySpring.dto.CommentDTO;
import com.example.SpotifySpring.exceptions.PostNotFoundException;
import com.example.SpotifySpring.exceptions.UserNotFoundException;
import com.example.SpotifySpring.mapper.CommentMapper;
import com.example.SpotifySpring.model.Comment;
import com.example.SpotifySpring.model.Post;
import com.example.SpotifySpring.model.User;
import com.example.SpotifySpring.repository.CommentRepository;
import com.example.SpotifySpring.repository.PostRepository;
import com.example.SpotifySpring.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final SpotifyAPIAuthService spotifyAPIAuthService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void save(CommentDTO commentDTO) throws IOException, ParseException, SpotifyWebApiException {
        Post post = postRepository.findById(commentDTO.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post " + commentDTO.getPostId().toString() + " not found" ));

        Comment comment = commentMapper.map(commentDTO, post, spotifyAPIAuthService.getCurrentUser());
        commentRepository.save(comment);
    }

    public List<CommentDTO> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post " + postId.toString() + " not found."));
        return commentRepository.findAllByPost(post)
                .stream()
                .map(commentMapper::mapToDTO)
                .collect(toList());
    }

    public List<CommentDTO> getAllCommentsForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User " + username + " not found."));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDTO)
                .collect(toList());
    }




}
