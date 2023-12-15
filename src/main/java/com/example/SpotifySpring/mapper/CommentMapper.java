package com.example.SpotifySpring.mapper;

import com.example.SpotifySpring.dto.CommentDTO;
import com.example.SpotifySpring.model.Comment;
import com.example.SpotifySpring.model.Post;
import com.example.SpotifySpring.model.User;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {


    @Mapping(target = "commentId", ignore = true)
    @Mapping(target = "text", source = "commentDTO.text")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    public abstract Comment map(CommentDTO commentDTO, Post post, User user);

    @Mapping(target = "id", source = "commentId")
    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "userName", source = "comment.getUser().getUsername")
    @Mapping(target = "duration", expression = "java(getDuration(comment))")
    public abstract CommentDTO mapToDTO(Comment comment);

    String getDuration(Comment comment) {
        return TimeAgo.using(comment.getCreatedDate().toEpochMilli());
    }
}
