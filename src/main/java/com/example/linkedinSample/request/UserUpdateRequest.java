package com.example.linkedinSample.request;

import com.example.linkedinSample.model.EGender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserUpdateRequest {
    private String name;
    private String password;
    private String aboutMe;
}
