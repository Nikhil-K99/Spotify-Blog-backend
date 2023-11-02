package com.example.SpotifySpring.model;

import com.example.SpotifySpring.enums.TopicType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Topic {

    @Id
    private Long topicId;

    private String topicSpotifyId;

    private TopicType topicType;

    @OneToMany(fetch = LAZY)
    private List<Post> posts;
}
