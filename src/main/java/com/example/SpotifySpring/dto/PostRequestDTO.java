package com.example.SpotifySpring.dto;

import com.example.SpotifySpring.enums.TopicType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDTO {
    private Long postId;
    private String postName;
    private String description;
    private TopicType topicType;
    private String topicSpotifyId;
}
