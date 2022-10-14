package com.example.linkedinSample.customValidation.checkDuplicateEmail;

import com.example.linkedinSample.exception.type.DuplicateFieldException;
import com.example.linkedinSample.repository.UsersRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckDuplicateEmailValidator implements ConstraintValidator<CheckDuplicateEmail, String> {
    public final UsersRepository usersRepository;

    public CheckDuplicateEmailValidator(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (usersRepository.findByEmail(email).isPresent()) {
            try {
                throw new DuplicateFieldException("Email is duplicate!");
            } catch (DuplicateFieldException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }
}
