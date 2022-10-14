package com.example.linkedinSample.customValidation.ckeckPassword;

import com.example.linkedinSample.customValidation.checkRoles.CheckRoles;
import com.example.linkedinSample.exception.type.InvalidPasswordException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckPasswordValidator implements ConstraintValidator<CheckPassword, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        final int UPPER_CASE = 0;
        final int LOWER_CASE = 1;
        final int DIGIT = 2;
        boolean[] isValid = new boolean[3];

        for (char character : password.toCharArray()) {
            if (Character.isUpperCase(character)) {
                isValid[UPPER_CASE] = true;
            } else if (Character.isLowerCase(character)) {
                isValid[LOWER_CASE] = true;
            } else if (Character.isDigit(character)) {
                isValid[DIGIT] = true;
            }

            if (isValid[UPPER_CASE] == true && isValid[LOWER_CASE] == true && isValid[DIGIT] == true) {
                break;
            }
        }
        if (isValid[UPPER_CASE] == false || isValid[LOWER_CASE] == false || isValid[DIGIT] == false) {
            try {
                throw new InvalidPasswordException(
                        "password must contain at least one uppercase letter, one lowercase letter and a number"
                );
            } catch (InvalidPasswordException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }
}
