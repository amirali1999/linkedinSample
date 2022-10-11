package com.example.linkedinSample.exception.type;

public class InvalidCharacterException extends Exception{
    private String message;
    public InvalidCharacterException(){
    }
    public InvalidCharacterException(String message){
        super(message);
        this.message = message;
    }
}
