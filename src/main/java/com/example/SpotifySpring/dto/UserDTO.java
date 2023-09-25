package com.example.SpotifySpring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String userName;
    private String email;
    private String profilePicture;
    private String spotifyUserId;
    private List<String> TopArtistsofMonth;

}
