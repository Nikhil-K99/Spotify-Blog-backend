package com.example.SpotifySpring.mapper;

import com.example.SpotifySpring.dto.PostRequestDTO;
import com.example.SpotifySpring.dto.PostResponseDTO;
import com.example.SpotifySpring.enums.VoteType;
import com.example.SpotifySpring.model.Post;
import com.example.SpotifySpring.model.Topic;
import com.example.SpotifySpring.model.User;
import com.example.SpotifySpring.model.Vote;
import com.example.SpotifySpring.repository.CommentRepository;
import com.example.SpotifySpring.repository.VoteRepository;
import com.example.SpotifySpring.service.SpotifyAPIAuthService;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import org.apache.hc.core5.http.ParseException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.util.Optional;


@Mapper(componentModel = "spring")
public abstract class PostMapper {

    private final CommentRepository commentRepository;
    private final VoteRepository voteRepository;
    private final SpotifyAPIAuthService spotifyAPIAuthService;

    protected PostMapper(CommentRepository commentRepository, VoteRepository voteRepository, SpotifyAPIAuthService spotifyAPIAuthService) {
        this.commentRepository = commentRepository;
        this.voteRepository = voteRepository;
        this.spotifyAPIAuthService = spotifyAPIAuthService;
    }

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "topic", source = "target")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "user", source = "user")
    public abstract Post map(PostRequestDTO postRequestDTO, Topic topic, User user);


    @Mapping(target = "topicSpotifyId", source = "topic.topicSpotifyId")
    @Mapping(target = "topicType", source = "topic.topicType")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "duration", source = "java(getDuration(post))")
    @Mapping(target = "commentCount", source = "java(getCommentCount(post))")
    @Mapping(target = "upVote", source = "java(isPostUpVoted(post))")
    @Mapping(target = "downVote", source = "java(isPostDownVoted(post))")
    public abstract PostResponseDTO maptoDTO(Post post);


    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }
 
    Integer getCommentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    private boolean checkVoteType(Post post, VoteType voteType) throws IOException, ParseException, SpotifyWebApiException {

        Optional<Vote> voteForPostByUser = voteRepository.findVoteByPostAndUser(post, spotifyAPIAuthService.getCurrentUser());
        return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType)).isPresent();
    }

    boolean isPostUpVoted(Post post) throws IOException, ParseException, SpotifyWebApiException {
        return checkVoteType(post, VoteType.UPVOTE);
    }

    boolean isPostDownVoted(Post post) throws IOException, ParseException, SpotifyWebApiException {
        return checkVoteType(post, VoteType.DOWNVOTE);
    }
}

