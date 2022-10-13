package com.example.linkedinSample.exception.handler;

import com.example.linkedinSample.exception.type.TokenRefreshException;
import com.example.linkedinSample.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class TokenControllerHandler {

    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Response handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
        List<String> list = new ArrayList<>();
        return new Response(HttpStatus.FORBIDDEN, ex.getMessage(),list,1);
    }
}