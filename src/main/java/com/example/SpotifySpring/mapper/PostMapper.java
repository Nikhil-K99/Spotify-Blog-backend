package com.example.SpotifySpring.mapper;

import com.example.SpotifySpring.dto.PostRequestDTO;
import com.example.SpotifySpring.dto.PostResponseDTO;
import com.example.SpotifySpring.dto.TopicDTO;
import com.example.SpotifySpring.enums.VoteType;
import com.example.SpotifySpring.model.Post;
import com.example.SpotifySpring.model.Topic;
import com.example.SpotifySpring.model.User;
import com.example.SpotifySpring.model.Vote;
import com.example.SpotifySpring.repository.CommentRepository;
import com.example.SpotifySpring.repository.VoteRepository;
import com.example.SpotifySpring.service.SpotifyAPIAuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.time.Instant;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class PostMapper {

    private final CommentRepository commentRepository;
    private final VoteRepository voteRepository;
    private final SpotifyAPIAuthService spotifyAPIAuthService;
    private final TopicMapper topicMapper;
    private static final Logger logger = LoggerFactory.getLogger(PostMapper.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public Post map(PostRequestDTO postRequestDTO, Topic topic, User user) {
        Post post = new Post();
        post.setPostName(postRequestDTO.getPostName());
        post.setDescription(postRequestDTO.getDescription());
        post.setVoteCount(0);
        post.setUser(user);
        post.setCreatedDate(Instant.now());
        post.setTopic(topic);

        return post;
    }

    public PostResponseDTO mapToDTO(Post post) throws IOException, SpotifyWebApiException, ParseException {

        logger.info("Mapping Post to PostResponseDTO");
        PostResponseDTO postResponseDTO = new PostResponseDTO();
        postResponseDTO.setPostId(post.getPostId());
        postResponseDTO.setPostName(post.getPostName());
        postResponseDTO.setDescription(post.getDescription());
//        postResponseDTO.setTopicType(post.getTopic().getTopicType());
//        postResponseDTO.setTopicId(post.getTopic().getTopicId());
        postResponseDTO.setUsername(post.getUser().getUsername());
        postResponseDTO.setVoteCount(post.getVoteCount());
        postResponseDTO.setCommentCount(getCommentCount(post));
        postResponseDTO.setDuration(getDuration(post));
        postResponseDTO.setUpVote(isPostUpVoted(post));
        postResponseDTO.setDownVote(isPostDownVoted(post));

        TopicDTO topicDTO = topicMapper.mapToDTO(post.getTopic());
        postResponseDTO.setTopicData(topicDTO);

        try {
            String json = objectMapper.writeValueAsString(postResponseDTO);
            logger.info("Mapped DTO as JSON: {}", json);
        } catch (Exception e) {
            logger.error("Error serializing DTO", e);
        }
        logger.info("Successfully mapped Post to PostResponseDTO");

        return postResponseDTO;
    }
//
//    @Autowired
//   CommentRepository commentRepository;
//    @Autowired
//    VoteRepository voteRepository;
//    @Autowired
//    SpotifyAPIAuthService spotifyAPIAuthService;
//
////    protected PostMapper(CommentRepository commentRepository, VoteRepository voteRepository, SpotifyAPIAuthService spotifyAPIAuthService) {
////        this.commentRepository = commentRepository;
////        this.voteRepository = voteRepository;
////        this.spotifyAPIAuthService = spotifyAPIAuthService;
////    }
//
//    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
//    @Mapping(target = "topic", source = "topic")
//    @Mapping(target = "voteCount", constant = "0")
//    @Mapping(target = "user", source = "user")
//    public abstract Post map(PostRequestDTO postRequestDTO, Topic topic, User user);
//
//
//    @Mapping(target = "topicId", source = "topic.topicId")
//    @Mapping(target = "topicType", source = "topic.topicType")
//    @Mapping(target = "username", source = "user.username")
//    @Mapping(target = "duration", expression = "java(getDuration(post))")
//    @Mapping(target = "commentCount", expression = "java(getCommentCount(post))")
//    @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))")
//    @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
//    public abstract PostResponseDTO mapToDTO(Post post) throws IOException, ParseException, SpotifyWebApiException;
//
//
    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }

    Integer getCommentCount(Post post) {
        return commentRepository.findAllByPost(post).size();
    }

    private boolean checkVoteType(Post post, VoteType voteType) throws IOException, SpotifyWebApiException, ParseException {

        Optional<Vote> voteForPostByUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, spotifyAPIAuthService.getCurrentUser());
        return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType)).isPresent();
    }

    boolean isPostUpVoted(Post post) throws IOException, SpotifyWebApiException, ParseException {
        return checkVoteType(post, VoteType.UPVOTE);
    }

    boolean isPostDownVoted(Post post) throws IOException, SpotifyWebApiException, ParseException {
        return checkVoteType(post, VoteType.DOWNVOTE);
    }
}

