package com.example.linkedinSample.customValidation.checkDuplicateUsername;

import com.example.linkedinSample.exception.type.DuplicateFieldException;
import com.example.linkedinSample.repository.UsersRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckDuplicateUsernameValidation implements ConstraintValidator<CheckDuplicateUsername, String> {
    public final UsersRepository usersRepository;

    public CheckDuplicateUsernameValidation(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (usersRepository.findByUsername(username).isPresent()) {
            try {
                throw new DuplicateFieldException("Username is duplicate!");
            } catch (DuplicateFieldException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }
}
