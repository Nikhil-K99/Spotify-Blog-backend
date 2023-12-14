package com.example.SpotifySpring.model;

import com.example.SpotifySpring.enums.TopicType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Topic {

    @Id
    private Long topicId;

    private String topicSpotifyId;

    private TopicType topicType;


}
