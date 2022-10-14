package com.example.linkedinSample.exception.handler;

import com.example.linkedinSample.exception.ErrorResponse;
import com.example.linkedinSample.exception.type.NotFoundException;
import com.example.linkedinSample.exception.type.TokenExpireException;
import com.example.linkedinSample.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
@ControllerAdvice
public class TokenExpireExceptionHandler {
    @ExceptionHandler(value = TokenExpireException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ResponseEntity<?> handlerTokenExpireException(TokenExpireException ex) {
        return new Response(HttpStatus.FORBIDDEN, ex.getMessage(),null,0).createResponseEntity();
    }
}
