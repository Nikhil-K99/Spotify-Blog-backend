package com.example.SpotifySpring.repository;

import com.example.SpotifySpring.model.Post;
import com.example.SpotifySpring.model.User;
import com.example.SpotifySpring.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User user);
}
