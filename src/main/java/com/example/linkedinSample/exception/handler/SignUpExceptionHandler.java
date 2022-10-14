package com.example.linkedinSample.exception.handler;

import com.example.linkedinSample.exception.ErrorResponse;
import com.example.linkedinSample.exception.type.SignUpException;
import com.example.linkedinSample.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class SignUpExceptionHandler {
    @ExceptionHandler(value = SignUpException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ResponseEntity<?> HandlerSignUpObjectException(SignUpException ex) {
        return new Response(HttpStatus.FORBIDDEN, ex.getMessage(),null,0).createResponseEntity();
    }
}
