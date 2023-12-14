package com.example.SpotifySpring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDTO {

    private String name;
    private String artistName;
    private String pictureUrl;
    private Long topicId;
    private String spotifyId;
}
