package com.example.linkedinSample.customValidation.checkRoles;

import com.example.linkedinSample.customValidation.checkDuplicateEmail.CheckDuplicateEmail;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckRolesValidator implements ConstraintValidator<CheckRoles, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
