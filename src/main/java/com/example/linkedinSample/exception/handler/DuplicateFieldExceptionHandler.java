package com.example.linkedinSample.exception.handler;

import com.example.linkedinSample.exception.ErrorResponse;
import com.example.linkedinSample.exception.type.DuplicateFieldException;
import com.example.linkedinSample.exception.type.EmptyFieldException;
import com.example.linkedinSample.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
@ControllerAdvice
public class DuplicateFieldExceptionHandler {
    @ExceptionHandler(value = com.example.linkedinSample.exception.type.DuplicateFieldException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public @ResponseBody ResponseEntity<?> handlerDuplicateFieldException(DuplicateFieldException ex) {
        return new Response(HttpStatus.NOT_ACCEPTABLE, ex.getMessage(),null,0).createResponseEntity();
    }
}
