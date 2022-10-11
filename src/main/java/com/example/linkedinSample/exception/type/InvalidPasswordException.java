package com.example.linkedinSample.exception.type;

public class InvalidPasswordException extends Exception{
    private String message;
    public InvalidPasswordException(){
    }
    public InvalidPasswordException(String message){
        super(message);
        this.message = message;
    }
}
