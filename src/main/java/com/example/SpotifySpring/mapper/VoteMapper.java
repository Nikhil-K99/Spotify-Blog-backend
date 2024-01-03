package com.example.SpotifySpring.mapper;

import com.example.SpotifySpring.dto.VoteDTO;
import com.example.SpotifySpring.model.Post;
import com.example.SpotifySpring.model.User;
import com.example.SpotifySpring.model.Vote;
import com.example.SpotifySpring.service.SpotifyAPIAuthService;
import lombok.AllArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
@AllArgsConstructor
public abstract class VoteMapper {

    private final SpotifyAPIAuthService spotifyAPIAuthService;


    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    public abstract Vote map(VoteDTO voteDTO, Post post, User user);

    @Mapping(target = "postId", source = "post.postId")
    public abstract VoteDTO mapToDTO(Vote vote);

}
