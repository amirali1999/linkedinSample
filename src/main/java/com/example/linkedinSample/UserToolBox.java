package com.example.linkedinSample;

import com.example.linkedinSample.entity.Roles;
import com.example.linkedinSample.entity.Users;
import com.example.linkedinSample.exception.type.*;
import com.example.linkedinSample.repository.RolesRepository;
import com.example.linkedinSample.repository.UsersRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Component
public class UserToolBox {
    public final UsersRepository usersRepository;
    public final PasswordEncoder encoder;
    public final RolesRepository rolesRepository;

    public UserToolBox(UsersRepository usersRepository, PasswordEncoder encoder, RolesRepository rolesRepository) {
        this.usersRepository = usersRepository;
        this.encoder = encoder;
        this.rolesRepository = rolesRepository;
    }

    public Users toolBox(Users users) throws EmptyFieldException, DuplicateFieldException, InvalidCharacterException,
            InvalidLengthException, InvalidPasswordException, InvalidEmailException, InvalidRolesException {
        checkEmptyFields(users);
        checkDuplicateUsername(users.getUsername());
        checkDuplicateEmail(users.getEmail());
        checkUsername(users.getUsername());
        checkPassword(users.getPassword());
        checkEmail(users.getEmail());
        users = checkRoles(users);
        users.setPassword(encodePassword(users.getPassword()));
        return users;
    }

    public void checkEmptyFields(Users users) throws EmptyFieldException {
        if (users.getUsername().isEmpty()) {
            throw new EmptyFieldException("Username's field is empty!");
        }
        if (users.getPassword().isEmpty()) {
            throw new EmptyFieldException("Password's field is empty!");
        }
        if (users.getEmail().isEmpty()) {
            throw new EmptyFieldException("Email's field is empty!");
        }
    }
    public void checkDuplicateUsername(String username) throws DuplicateFieldException {
        if (usersRepository.findByUsername(username).isPresent()) {
            throw new DuplicateFieldException("Username is duplicate!");
        }
    }
    public void checkDuplicateEmail(String email) throws DuplicateFieldException{
        if (usersRepository.findByEmail(email).isPresent()) {
            throw new DuplicateFieldException("Email is duplicate!");
        }
    }
    public void checkUsername(String username) throws InvalidLengthException, InvalidCharacterException {
        checkUsernameLength(username);
        checkValidCharacter(username);
    }
    public void checkUsernameLength(String username) throws InvalidLengthException {
        if ((username.length() < 3)) {
            throw new InvalidLengthException("The username is shorter than the allowed range!");
        }
    }
    public void checkValidCharacter(String username) throws InvalidCharacterException {
        if (!username.matches("\\w+")) {
            throw new InvalidCharacterException("The username contain invalid character!");
        }
    }
    public void checkPassword(String password) throws InvalidPasswordException {
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
            throw new InvalidPasswordException(
                    "password must contain at least one uppercase letter, one lowercase letter and a number"
            );
        }
    }
    public void checkEmail(String email) throws InvalidEmailException {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches() == false) {
            throw new InvalidEmailException("Email should contain atleast one @ and dot");
        }
    }
    public Users checkRoles(Users users) throws InvalidRolesException {
        if (users.getRoles() == null) {
            users.setRoles(defaultRole());
        } else {
            for (Roles role : users.getRoles()) {
                List<Roles> roles = rolesRepository.findAll();
                if (!roles.contains(role)) {
                    throw new InvalidRolesException("role" + role.getName() + "not founded!");
                }
            }
        }
        return users;
    }
    public Set<Roles> defaultRole() {
        Roles roles = rolesRepository.findByName("user").orElse(null);
        Set<Roles> listOfRoles = new HashSet<>();
        listOfRoles.add(roles);
        return listOfRoles;
    }
    public String encodePassword(String password){
        return encoder.encode(password);
    }
}
