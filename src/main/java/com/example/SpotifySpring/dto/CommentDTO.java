package com.example.SpotifySpring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    private Long postId;
    private String duration;
    private String text;
    private String userName;
}
