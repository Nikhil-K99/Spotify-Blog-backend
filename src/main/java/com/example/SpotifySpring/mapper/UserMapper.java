package com.example.SpotifySpring.mapper;

import com.example.SpotifySpring.dto.UserDTO;
import com.example.SpotifySpring.model.User;
import com.example.SpotifySpring.service.SpotifyAPIService;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.requests.data.users_profile.GetUsersProfileRequest;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class UserMapper {

    private final SpotifyApi spotifyApi;
    private final SpotifyAPIService spotifyAPIService;

    public User map(UserDTO userDTO){
        User user = new User();
        user.setUsername(userDTO.getUserName());
        user.setUserId(userDTO.getUserId());
        user.setEmail(userDTO.getEmail());

        return user;
    }

    public UserDTO maptoDTO(User user) throws SpotifyWebApiException, IOException, ParseException {
        String userId = user.getUserId();

        spotifyAPIService.checkTokenExpiration();
        GetUsersProfileRequest getUsersProfileRequest = spotifyApi.getUsersProfile(userId)
                .build();

        se.michaelthelin.spotify.model_objects.specification.User spotifyUser = getUsersProfileRequest.execute();
        String userImage = Arrays.stream(spotifyUser.getImages()).findFirst().get().getUrl();
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userId);
        userDTO.setUserName(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setProfilePicture(userImage);

        return userDTO;
    }
}
