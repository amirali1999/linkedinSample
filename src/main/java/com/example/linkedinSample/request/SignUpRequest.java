package com.example.linkedinSample.request;

import com.example.linkedinSample.customValidation.checkDuplicateEmail.CheckDuplicateEmail;
import com.example.linkedinSample.customValidation.checkDuplicateUsername.CheckDuplicateUsername;
import com.example.linkedinSample.customValidation.checkEmail.CheckEmail;
import com.example.linkedinSample.customValidation.checkUsernameLength.CheckUsernameLength;
import com.example.linkedinSample.customValidation.checkValidCharacter.CheckValidCharacter;
import com.example.linkedinSample.customValidation.ckeckPassword.CheckPassword;
import com.example.linkedinSample.model.EGender;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SignUpRequest {
    private String name;
    @NotBlank
//    @CheckDuplicateUsername
    @CheckUsernameLength
    @CheckValidCharacter
    private String username;
    @NotBlank
    @CheckPassword
    private String password;
    @NotBlank
    @CheckDuplicateEmail
    @CheckEmail
    private String email;
    private EGender gender;
}
