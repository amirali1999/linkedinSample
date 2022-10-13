    package com.example.linkedinSample.response;

import com.example.linkedinSample.model.EGender;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String name;
    private String email;
    private EGender gender;
    private List<String> roles;
    private String refreshToken;

    public JwtResponse(String accessToken, String refreshToken, Long id, String username, String name, String email,
                       EGender gender, List<String> roles) {
        this.token = accessToken;
        this.refreshToken = refreshToken;
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.roles = roles;
    }
}
