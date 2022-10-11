package com.example.linkedinSample.exception.type;

public class InvalidGenderException extends Exception{
    private String message;
    public InvalidGenderException(){
    }
    public InvalidGenderException(String message){
        super(message);
        this.message = message;
    }
}
