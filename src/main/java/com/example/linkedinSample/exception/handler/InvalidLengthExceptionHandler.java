package com.example.linkedinSample.exception.handler;

import com.example.linkedinSample.exception.ErrorResponse;
import com.example.linkedinSample.exception.type.InvalidLengthException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
@ControllerAdvice
public class InvalidLengthExceptionHandler {
    @ExceptionHandler(value = com.example.linkedinSample.exception.type.InvalidLengthException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public @ResponseBody ErrorResponse handlerInvalidLengthException(InvalidLengthException ex) {
        return new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), ex.getMessage());
    }
}
