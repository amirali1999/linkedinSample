package com.example.linkedinSample.exception.type;

public class InvalidEmailException extends Exception{
    private String message;
    public InvalidEmailException(){
    }
    public InvalidEmailException(String message){
        super(message);
        this.message = message;
    }
}
