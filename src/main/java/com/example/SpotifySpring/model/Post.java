package com.example.SpotifySpring.model;

import com.example.SpotifySpring.enums.SubjectType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    private String postName;
    @Lob
    private String description;
    private Integer voteCount = 0;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spotifyUserId", referencedColumnName = "spotifyUserId")
    private User user;
    private Instant createdDate;
    private SubjectType subjectType;
    private Long subjectId;
}
