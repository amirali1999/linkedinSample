package com.example.linkedinSample.customValidation.checkValidCharacter;

import com.example.linkedinSample.customValidation.checkRoles.CheckRoles;
import com.example.linkedinSample.exception.type.InvalidCharacterException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckValidCharacterValidator implements ConstraintValidator<CheckValidCharacter, String> {
    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (!username.matches("\\w+")) {
            try {
                throw new InvalidCharacterException("The username contain invalid character!");
            } catch (InvalidCharacterException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }
}
