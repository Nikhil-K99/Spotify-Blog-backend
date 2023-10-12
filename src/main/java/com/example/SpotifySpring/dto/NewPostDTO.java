package com.example.SpotifySpring.dto;

import com.example.SpotifySpring.enums.SubjectType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewPostDTO {
    private Long postId;
    private String postName;
    private String description;
    private SubjectType subjectType;
    private Long subjectId;
}
