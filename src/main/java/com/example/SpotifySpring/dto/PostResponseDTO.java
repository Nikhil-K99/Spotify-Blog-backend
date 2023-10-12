package com.example.SpotifySpring.dto;

import com.example.SpotifySpring.enums.SubjectType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDTO {
    private Long postId;
    private String postName;
    private String description;
    private SubjectType subjectType;
    private Long subjectId;
    private String username;
    private Integer voteCount;
    private Integer commentCount;
    private String duration;
    private boolean upVote;
    private boolean downVote;
}
