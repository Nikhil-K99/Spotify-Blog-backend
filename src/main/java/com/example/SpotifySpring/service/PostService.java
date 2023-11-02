package com.example.SpotifySpring.service;

import com.example.SpotifySpring.dto.PostRequestDTO;
import com.example.SpotifySpring.dto.PostResponseDTO;
import com.example.SpotifySpring.exceptions.PostNotFoundException;
import com.example.SpotifySpring.exceptions.TopicNotFoundException;
import com.example.SpotifySpring.exceptions.UserNotFoundException;
import com.example.SpotifySpring.mapper.PostMapper;
import com.example.SpotifySpring.model.Post;
import com.example.SpotifySpring.model.Topic;
import com.example.SpotifySpring.model.User;
import com.example.SpotifySpring.repository.PostRepository;
import com.example.SpotifySpring.repository.TopicRepository;
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
public class PostService {

    private final PostRepository postRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final SpotifyAPIService spotifyAPIService;
    private final PostMapper postMapper;

    public void save(PostRequestDTO postRequestDTO) throws IOException, ParseException, SpotifyWebApiException {
        //If the topic does not exist, add the topic to the database
        if (!topicRepository.existsByTopicSpotifyIdAndTopicType(postRequestDTO.getTopicSpotifyId(), postRequestDTO.getTopicType())) {
            Topic topic = new Topic();
            topic.setTopicType(postRequestDTO.getTopicType());
            topic.setTopicSpotifyId(postRequestDTO.getTopicSpotifyId());

            topicRepository.save(topic);
            postRepository.save(postMapper.map(postRequestDTO, topic, spotifyAPIService.getCurrentUser()));
        } else {
            Topic topic = topicRepository.findByTopicSpotifyIdAndTopicType(postRequestDTO.getTopicSpotifyId(), postRequestDTO.getTopicType())
                    .orElseThrow(() -> new TopicNotFoundException(postRequestDTO.getTopicType().toString() + " " + postRequestDTO.getTopicSpotifyId() + " not found."));
            postRepository.save(postMapper.map(postRequestDTO, topic, spotifyAPIService.getCurrentUser()));
        }
    }

    public PostResponseDTO getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post " + postId.toString() + "not found."));
        return postMapper.maptoDTO(post);
    }

    public List<PostResponseDTO> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::maptoDTO)
                .collect(toList());
    }

    public List<PostResponseDTO> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Could not find User: " + username));

        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::maptoDTO)
                .collect(toList());
    }

    public List<PostResponseDTO> getPostsByTopic(Long topicId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException( "Topic " + topicId.toString() + " not found."));
        return postRepository.findAllByTopic(topic)
                .stream()
                .map(postMapper::maptoDTO)
                .collect(toList());
    }

}
