package com.example.linkedinSample.controller;

import com.example.linkedinSample.config.security.jwt.JwtUtils;
import com.example.linkedinSample.model.RefreshToken;
import com.example.linkedinSample.request.LoginRequest;
import com.example.linkedinSample.request.SignUpRequest;
import com.example.linkedinSample.exception.type.*;
import com.example.linkedinSample.request.TokenRefreshRequest;
import com.example.linkedinSample.response.Response;
import com.example.linkedinSample.response.TokenRefreshResponse;
import com.example.linkedinSample.service.AuthService;
import com.example.linkedinSample.service.JwtBlacklistService;
import com.example.linkedinSample.service.RefreshTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private final AuthService authService;

    private final RefreshTokenService refreshTokenService;
    private final JwtUtils jwtUtils;
    private final MessageSource messageSource;

    private final JwtBlacklistService jwtBlacklistService;

    @Autowired
    public AuthController(AuthService authService, RefreshTokenService refreshTokenService, JwtUtils jwtUtils, MessageSource messageSource, JwtBlacklistService jwtBlacklistService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.jwtUtils = jwtUtils;
        this.messageSource = messageSource;
        this.jwtBlacklistService = jwtBlacklistService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.authenticateUser(loginRequest).createResponseEntity();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest)
            throws InvalidCharacterException, DuplicateFieldException, InvalidRolesException, InvalidPasswordException,
            InvalidLengthException, InvalidEmailException, EmptyFieldException {
        return authService.registerUser(signUpRequest).createResponseEntity();
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUsers)
                .map(users -> {
                    String token = jwtUtils.generateTokenFromUsername(users.getUsername());
                    return new Response(HttpStatus.OK,
                            messageSource.getMessage("get.token.successfully",
                                    null,
                                    LocaleContextHolder.getLocale()
                            ),
                            new TokenRefreshResponse(token, requestRefreshToken),
                            1);
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!")).createResponseEntity();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        return authService.logoutUser().createResponseEntity();
    }
}
