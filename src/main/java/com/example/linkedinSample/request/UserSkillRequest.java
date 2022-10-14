package com.example.linkedinSample.request;

import com.example.linkedinSample.model.Subject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserSkillRequest {
    private String name;
    private String description;
    private Subject subject;
}
