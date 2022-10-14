package com.example.linkedinSample.controller;

import com.example.linkedinSample.FeignClientInterceptor;
import com.example.linkedinSample.model.Users;
import com.example.linkedinSample.exception.type.*;
import com.example.linkedinSample.request.UserUpdateRequest;
import com.example.linkedinSample.service.JwtBlacklistService;
import com.example.linkedinSample.service.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "users")
public class UsersController {
    private final UsersService usersService;
    private final FeignClientInterceptor feignClientInterceptor;
    private final JwtBlacklistService jwtBlacklistService;

    public UsersController(
            UsersService usersService,
            FeignClientInterceptor
                    feignClientInterceptor,
            JwtBlacklistService jwtBlacklistService) {
        this.usersService = usersService;
        this.feignClientInterceptor = feignClientInterceptor;
        this.jwtBlacklistService = jwtBlacklistService;
    }
    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping(path = "getallusers/{page}")
    public ResponseEntity<?> getUsers(@PathVariable("page") int page) throws TokenExpireException {
        jwtBlacklistService.checkAccessTokenExpire
                (feignClientInterceptor.getBearerTokenHeader().replace("Bearer ",""));
        return usersService.getUsers(page).createResponseEntity();
    }
    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping(path = "adduser")
    public ResponseEntity<?> postUsers(@Valid @RequestBody Users users) throws InvalidCharacterException,
            InvalidLengthException, InvalidPasswordException, EmptyFieldException, DuplicateFieldException,
            InvalidEmailException, InvalidRolesException {
        return usersService.postUsers(users).createResponseEntity();
    }
    @PreAuthorize("hasAnyAuthority('admin')")
    @DeleteMapping(path = "deleteuser")
    public ResponseEntity<?> deleteUsers(@Valid @RequestBody Users users) throws NotFoundException {
        return usersService.deleteUsers(users).createResponseEntity();
    }
    @PreAuthorize("hasAnyAuthority('admin')")
    @PutMapping(path = "updateuser/{usersname}")
    public ResponseEntity<?> putUsers(@PathVariable("usersname") String usersName, @Valid @RequestBody Users users)
            throws NotFoundException, DuplicateFieldException, InvalidCharacterException, InvalidLengthException,
            InvalidPasswordException, InvalidEmailException, InvalidGenderException {
        return usersService.putUsers(users, usersName).createResponseEntity();
    }
    @PreAuthorize("hasAnyAuthority('admin','user')")
    @PutMapping(path = "updateprofile")
    public ResponseEntity<?> userUpdateRequest(@Valid @RequestBody UserUpdateRequest userUpdateRequest)
            throws NotFoundException, DuplicateFieldException, InvalidCharacterException, InvalidLengthException,
            InvalidPasswordException, InvalidEmailException, InvalidGenderException {
        return usersService.userUpdateRequest(userUpdateRequest).createResponseEntity();
    }
    @PreAuthorize("hasAnyAuthority('admin','user')")
    @PutMapping(path = "addskilltouser/{skillid}")
    public ResponseEntity<?> addSkillToUser(@PathVariable("skillid") long skillId)
            throws DuplicateFieldException, NotFoundException {
        return usersService.addSkillToUser(skillId).createResponseEntity();
    }
    @PreAuthorize("hasAnyAuthority('admin','user')")
    @PutMapping(path = "removeskilltouser/{skillid}")
    public ResponseEntity<?> removeSkillToUser(@PathVariable("skillid") long skillId) throws NotFoundException {
        return usersService.removeSkillToUser(skillId).createResponseEntity();
    }
}
