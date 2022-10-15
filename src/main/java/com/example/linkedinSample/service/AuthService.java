package com.example.linkedinSample.service;

import com.example.linkedinSample.FeignClientInterceptor;
import com.example.linkedinSample.UserToolBox;
import com.example.linkedinSample.config.security.jwt.JwtUtils;
import com.example.linkedinSample.config.security.services.UserDetailsImpl;
import com.example.linkedinSample.model.RefreshToken;
import com.example.linkedinSample.repository.RefreshTokenRepository;
import com.example.linkedinSample.request.LoginRequest;
import com.example.linkedinSample.request.SignUpRequest;
import com.example.linkedinSample.model.Users;
import com.example.linkedinSample.exception.type.*;
import com.example.linkedinSample.repository.RolesRepository;
import com.example.linkedinSample.repository.UsersRepository;
import com.example.linkedinSample.response.JwtResponse;
import com.example.linkedinSample.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final UsersService usersService;
    private final UserToolBox userToolBox;
    private final JwtBlacklistService jwtBlacklistService;
    private final FeignClientInterceptor feignClientInterceptor;
    private final RefreshTokenRepository refreshTokenRepository;

    private final RefreshTokenService refreshTokenService;

    public AuthService(AuthenticationManager authenticationManager,
                       UsersRepository usersRepository,
                       RolesRepository rolesRepository,
                       PasswordEncoder encoder,
                       JwtUtils jwtUtils, UsersService usersService, UserToolBox userToolBox,
                       JwtBlacklistService jwtBlacklistService, FeignClientInterceptor feignClientInterceptor,
                       RefreshTokenRepository refreshTokenRepository, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.usersService = usersService;
        this.userToolBox = userToolBox;
        this.jwtBlacklistService = jwtBlacklistService;
        this.feignClientInterceptor = feignClientInterceptor;
        this.refreshTokenRepository = refreshTokenRepository;
        this.refreshTokenService = refreshTokenService;
    }

    //TODO-->change ResponseEntity
    public Response authenticateUser(LoginRequest loginRequest) {
        //Authentication
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
//        System.out.println(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //create jwt access token
        String jwt = jwtUtils.generateJwtToken(authentication);
        //get authenticate user's details like email and username to userDetails
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        //get roles
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return new Response(HttpStatus.OK,"user.signin.successfully",new JwtResponse(jwt,refreshToken.getToken(),
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getName(),
                userDetails.getEmail(),
                userDetails.getGender(),
                roles),1);
    }

    public Response registerUser(SignUpRequest signUpRequest)
            throws InvalidCharacterException, DuplicateFieldException, InvalidRolesException, InvalidPasswordException,
            InvalidLengthException, InvalidEmailException, EmptyFieldException {
        Users users = new Users(
                signUpRequest.getName(),
                signUpRequest.getUsername(),
                signUpRequest.getPassword(),
                signUpRequest.getEmail(),
                signUpRequest.getGender()
        );
        users = userToolBox.toolBox(users);
        usersRepository.save(users);
        return new Response(HttpStatus.OK, "user.registered.successfully", signUpRequest, 1);
    }
    @Transactional
    public Response logoutUser(){
        String accessToken = feignClientInterceptor.getBearerTokenHeader().replace("Bearer ","");
        String username = jwtUtils.getUserNameFromJwtToken(accessToken);
        Users users = usersRepository.findByUsername(username).orElse(null);
        refreshTokenRepository.deleteAccessTokens(users.getId());
        jwtBlacklistService.saveTokenToBlackList(accessToken,jwtUtils.getExpireDateFromJwtToken(accessToken));
        return new Response(HttpStatus.OK, "user.logout.successfully",null, 1);
    }
}
