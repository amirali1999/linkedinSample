package com.example.linkedinSample.request;

import com.example.linkedinSample.model.EGender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
    private String name;
    private String username;
    private String password;
    private String email;
    private EGender gender;
}
