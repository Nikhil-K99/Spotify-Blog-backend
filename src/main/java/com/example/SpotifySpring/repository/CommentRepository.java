package com.example.SpotifySpring.repository;

import com.example.SpotifySpring.model.Comment;
import com.example.SpotifySpring.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost(Post post);
}
