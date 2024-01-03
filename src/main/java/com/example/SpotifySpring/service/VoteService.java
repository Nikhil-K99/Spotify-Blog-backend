package com.example.SpotifySpring.service;

import com.example.SpotifySpring.dto.VoteDTO;
import com.example.SpotifySpring.exceptions.PostNotFoundException;
import com.example.SpotifySpring.exceptions.VoteAlreadyExistsException;
import com.example.SpotifySpring.mapper.VoteMapper;
import com.example.SpotifySpring.model.Post;
import com.example.SpotifySpring.model.Vote;
import com.example.SpotifySpring.repository.PostRepository;
import com.example.SpotifySpring.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.util.Optional;

import static com.example.SpotifySpring.enums.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final VoteMapper voteMapper;
    private final PostRepository postRepository;
    private final SpotifyAPIAuthService spotifyAPIAuthService;

    public void vote(VoteDTO voteDTO) throws IOException, ParseException, SpotifyWebApiException {
        Post post = postRepository.findById(voteDTO.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post not found with ID " + voteDTO.getPostId()));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByByPostAndUserOrderByVoteIdDesc(post, spotifyAPIAuthService.getCurrentUser());
        if (voteByPostAndUser.isPresent() && voteByPostAndUser.equals(voteDTO.getVoteType())) {
            throw new VoteAlreadyExistsException("You have already " + voteDTO.getVoteType().toString() + "'d this post.");
        }
        if (UPVOTE.equals(voteDTO.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(voteMapper.map(voteDTO, post, spotifyAPIAuthService.getCurrentUser()));
        postRepository.save(post);
    }
}
