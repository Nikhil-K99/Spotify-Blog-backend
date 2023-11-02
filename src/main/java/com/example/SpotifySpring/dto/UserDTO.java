package com.example.SpotifySpring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String userName;
    private String email;
    private String profilePicture;
    private String userId;


}
