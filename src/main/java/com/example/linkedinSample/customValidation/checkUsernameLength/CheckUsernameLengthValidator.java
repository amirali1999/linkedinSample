package com.example.linkedinSample.customValidation.checkUsernameLength;

import com.example.linkedinSample.customValidation.checkRoles.CheckRoles;
import com.example.linkedinSample.exception.type.InvalidLengthException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckUsernameLengthValidator implements ConstraintValidator<CheckUsernameLength, String> {
    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {

        if ((username.length() < 3)) {
            try {
                throw new InvalidLengthException("The username is shorter than the allowed range!");
            } catch (InvalidLengthException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }
}
