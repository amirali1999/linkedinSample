package com.example.linkedinSample.customValidation.checkEmail;

import com.example.linkedinSample.exception.type.InvalidEmailException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckEmailValidator implements ConstraintValidator<CheckEmail, String> {
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches() == false) {
            try {
                throw new InvalidEmailException("Email should contain atleast one @ and dot");
            } catch (InvalidEmailException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }
}
