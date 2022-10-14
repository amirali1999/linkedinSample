package com.example.linkedinSample.exception.handler;

import com.example.linkedinSample.exception.ErrorResponse;
import com.example.linkedinSample.exception.type.InvalidEmailException;
import com.example.linkedinSample.exception.type.InvalidPasswordException;
import com.example.linkedinSample.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class InvalidEmailExceptionHandler {
    @ExceptionHandler(value = com.example.linkedinSample.exception.type.InvalidEmailException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public @ResponseBody ResponseEntity<?> handlerInvalidEmailException(InvalidEmailException ex) {
        return new Response(HttpStatus.NOT_ACCEPTABLE, ex.getMessage(),null,0).createResponseEntity();
    }
}
