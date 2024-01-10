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
import org.springframework.beans.factory.annotation.Autowired;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.util.Optional;


@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
   CommentRepository commentRepository;
    @Autowired
    VoteRepository voteRepository;
    @Autowired
    SpotifyAPIAuthService spotifyAPIAuthService;

//    protected PostMapper(CommentRepository commentRepository, VoteRepository voteRepository, SpotifyAPIAuthService spotifyAPIAuthService) {
//        this.commentRepository = commentRepository;
//        this.voteRepository = voteRepository;
//        this.spotifyAPIAuthService = spotifyAPIAuthService;
//    }

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "topic", source = "topic")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "user", source = "user")
    public abstract Post map(PostRequestDTO postRequestDTO, Topic topic, User user);


    @Mapping(target = "topicId", source = "topic.topicId")
    @Mapping(target = "topicType", source = "topic.topicType")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    @Mapping(target = "commentCount", expression = "java(getCommentCount(post))")
    @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))")
    @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
    public abstract PostResponseDTO mapToDTO(Post post) throws IOException, ParseException, SpotifyWebApiException;


    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }
 
    Integer getCommentCount(Post post) {
        return commentRepository.findAllByPost(post).size();
    }

    private boolean checkVoteType(Post post, VoteType voteType) throws IOException, ParseException, SpotifyWebApiException {

        Optional<Vote> voteForPostByUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, spotifyAPIAuthService.getCurrentUser());
        return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType)).isPresent();
    }

    boolean isPostUpVoted(Post post) throws IOException, ParseException, SpotifyWebApiException {
        return checkVoteType(post, VoteType.UPVOTE);
    }

    boolean isPostDownVoted(Post post) throws IOException, ParseException, SpotifyWebApiException {
        return checkVoteType(post, VoteType.DOWNVOTE);
    }
}

