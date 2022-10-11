package com.example.linkedinSample.controller;

import com.example.linkedinSample.Response;
import com.example.linkedinSample.entity.Users;
import com.example.linkedinSample.exception.type.*;
import com.example.linkedinSample.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "users")
public class UsersController {
    @Autowired
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping(path = "getallusers/{page}")
    public ResponseEntity<?> getUsers(@PathVariable("page") int page) {
        return usersService.getUsers(page).createResponseEntity();
    }

    @PostMapping(path = "adduser")
    public ResponseEntity<?> postUsers(@Valid @RequestBody Users users) throws InvalidCharacterException,
            InvalidLengthException, InvalidPasswordException, EmptyFieldException, DuplicateFieldException,
            InvalidEmailException, InvalidRolesException {
        return usersService.postUsers(users).createResponseEntity();
    }

    @DeleteMapping(path = "deleteuser")
    public ResponseEntity<?> deleteUsers(@Valid @RequestBody Users users) throws NotFoundException {
        return usersService.deleteUsers(users).createResponseEntity();
    }

    @PutMapping(path = "updateuser/{usersname}")
    public ResponseEntity<?> putUsers(@PathVariable("usersname") String usersName, @Valid @RequestBody Users users)
            throws NotFoundException, DuplicateFieldException, InvalidCharacterException, InvalidLengthException,
            InvalidPasswordException, InvalidEmailException, InvalidGenderException {
        return usersService.putUsers(users, usersName).createResponseEntity();
    }
}
