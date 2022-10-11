package com.example.linkedinSample.service;

import com.example.linkedinSample.Response;
import com.example.linkedinSample.entity.EGender;
import com.example.linkedinSample.entity.Roles;
import com.example.linkedinSample.entity.Users;
import com.example.linkedinSample.exception.type.*;
import com.example.linkedinSample.repository.RolesRepository;
import com.example.linkedinSample.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UsersService {
    @Autowired
    private final UsersRepository usersRepository;
    @Autowired
    private final RolesRepository rolesRepository;

    public UsersService(UsersRepository usersRepository, RolesRepository rolesRepository) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
    }

    public Response getUsers(int page) {
        Page<Users> pages = usersRepository.findAll(PageRequest.of(page-1,10));
        return new Response(HttpStatus.OK,
                "Get Users successfully!",
                pages.get(),
                pages.getTotalPages());
    }

    public Response postUsers(Users users)
            throws InvalidCharacterException, InvalidLengthException, InvalidPasswordException, EmptyFieldException,
            DuplicateFieldException, InvalidEmailException, InvalidRolesException {
        checkEmptyFields(users);
        checkDuplicateUsername(users.getUsername());
        checkDuplicateEmail(users.getEmail());
        checkUsername(users.getUsername());
        checkPassword(users.getPassword());
        checkEmail(users.getEmail());
        users = checkRoles(users);
        usersRepository.save(users);
        return new Response(HttpStatus.OK, "Add user successfully!", users, 1);
    }

    public Response deleteUsers(Users users) throws NotFoundException {
        users = usersRepository.findByUsername(users.getUsername())
                .orElseThrow(()-> new NotFoundException("User not found!"));
        usersRepository.delete(users);
        return new Response(HttpStatus.OK, "Delete Users successfully!",users, 1);
    }

    public Response putUsers(Users users,String usersName)
            throws NotFoundException, DuplicateFieldException, InvalidCharacterException, InvalidLengthException,
            InvalidPasswordException, InvalidEmailException, InvalidGenderException {
        Users previousUsers = usersRepository.findByUsername(usersName)
                .orElseThrow(()->new NotFoundException("User not found!"));
        if(users.getName() != null){
            previousUsers.setName(users.getName());
        }
        if(users.getUsername() != null){
            checkDuplicateUsername(users.getUsername());
            checkUsername(users.getUsername());
            previousUsers.setUsername(users.getUsername());
        }
        if(users.getPassword() != null){
            checkPassword(users.getPassword());
            previousUsers.setPassword(users.getPassword());
        }
        if(users.getEmail() != null){
            checkDuplicateEmail(users.getEmail());
            checkEmail(users.getEmail());
            previousUsers.setEmail(users.getEmail());
        }
        if(users.getGender() != null){
            previousUsers.setGender(users.getGender());
        }
        usersRepository.save(previousUsers);
        return new Response(HttpStatus.OK, "Update Users successfully!", previousUsers, 1);
    }

    private void checkEmptyFields(Users users) throws EmptyFieldException {
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

    private void checkDuplicateUsername(String username) throws DuplicateFieldException{
        if (usersRepository.findByUsername(username).isPresent()) {
            throw new DuplicateFieldException("Username is duplicate!");
        }
    }

    private void checkDuplicateEmail(String email) throws DuplicateFieldException{
        if (usersRepository.findByEmail(email).isPresent()) {
            throw new DuplicateFieldException("Email is duplicate!");
        }
    }

    private void checkUsername(String username) throws InvalidLengthException, InvalidCharacterException {
        checkUsernameLength(username);
        checkValidCharacter(username);
    }

    private void checkUsernameLength(String username) throws InvalidLengthException {
        if ((username.length() < 3)) {
            throw new InvalidLengthException("The username is shorter than the allowed range!");
        }
    }

    private void checkValidCharacter(String username) throws InvalidCharacterException {
        if (!username.matches("\\w+")) {
            throw new InvalidCharacterException("The username contain invalid character!");
        }
    }

    private void checkPassword(String password) throws InvalidPasswordException {
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

    private void checkEmail(String email) throws InvalidEmailException {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches() == false) {
            throw new InvalidEmailException("Email should contain atleast one @ and dot");
        }
    }

    private Users checkRoles(Users users) throws InvalidRolesException {
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

    private Set<Roles> defaultRole() {
        Roles roles = rolesRepository.findByName("user").orElse(null);
        Set<Roles> listOfRoles = new HashSet<>();
        listOfRoles.add(roles);
        return listOfRoles;
    }

}
