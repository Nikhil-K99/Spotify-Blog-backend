package com.example.SpotifySpring.dto;

import com.example.SpotifySpring.enums.TopicType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicDTO {

    private String trackName;
    private String artistName;
    private String albumName;
    private String pictureUrl;
    private Long topicId;
    private String spotifyId;
    private TopicType topicType;
}
