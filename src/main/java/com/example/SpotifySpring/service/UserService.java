package com.example.SpotifySpring.service;

import com.example.SpotifySpring.dto.UserDTO;
import com.example.SpotifySpring.exceptions.UserNotFoundException;
import com.example.SpotifySpring.mapper.UserMapper;
import com.example.SpotifySpring.model.User;
import com.example.SpotifySpring.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDTO getUserbyId(String id) throws SpotifyWebApiException, IOException, ParseException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User: " + id + " not found"));
        return userMapper.maptoDTO(user);
    }

    public void saveNewUser(User user){
        if (!userRepository.existsById(user.getUserId())){
            userRepository.save(user);
        }
    }



}

