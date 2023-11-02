package com.example.SpotifySpring.repository;

import com.example.SpotifySpring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Override
    boolean existsById(String s);

    Optional<User> findByUsername(String username);
}
