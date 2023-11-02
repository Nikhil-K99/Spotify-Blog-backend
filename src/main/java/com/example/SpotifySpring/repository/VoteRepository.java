package com.example.SpotifySpring.repository;

import com.example.SpotifySpring.model.Post;
import com.example.SpotifySpring.model.User;
import com.example.SpotifySpring.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query("SELECT v FROM Vote v WHERE v.post = :post AND v.user = :user")
    Optional<Vote> findVoteByPostAndUser(@Param("post") Post post, @Param("user") User user);
}
