package com.example.SpotifySpring.repository;

import com.example.SpotifySpring.enums.TopicType;
import com.example.SpotifySpring.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    boolean existsByTopicSpotifyIdAndTopicType(String topicSpotifyId, TopicType topicType);

    Optional<Topic> findByTopicSpotifyIdAndTopicType(String topicSpotifyId, TopicType topicType);
}
