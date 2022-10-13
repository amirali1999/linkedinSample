package com.example.linkedinSample.service;

import com.example.linkedinSample.UserToolBox;
import com.example.linkedinSample.response.Response;
import com.example.linkedinSample.model.Users;
import com.example.linkedinSample.exception.type.*;
import com.example.linkedinSample.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    @Autowired
    private final UsersRepository usersRepository;
    @Autowired
    private final UserToolBox userToolBox;

    public UsersService(UsersRepository usersRepository, UserToolBox userToolBox) {
        this.usersRepository = usersRepository;
        this.userToolBox = userToolBox;
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
        users = userToolBox.toolBox(users);
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
            userToolBox.checkDuplicateUsername(users.getUsername());
            userToolBox.checkUsername(users.getUsername());
            previousUsers.setUsername(users.getUsername());
        }
        if(users.getPassword() != null){
            userToolBox.checkPassword(users.getPassword());
            previousUsers.setPassword(users.getPassword());
        }
        if(users.getEmail() != null){
            userToolBox.checkDuplicateEmail(users.getEmail());
            userToolBox.checkEmail(users.getEmail());
            previousUsers.setEmail(users.getEmail());
        }
        if(users.getGender() != null){
            previousUsers.setGender(users.getGender());
        }
        usersRepository.save(previousUsers);
        return new Response(HttpStatus.OK, "Update Users successfully!", previousUsers, 1);
    }
}
