package com.example.linkedinSample.exception.type;

public class InvalidLengthException extends Exception{
    private String message;
    public InvalidLengthException(){
    }
    public InvalidLengthException(String message){
        super(message);
        this.message = message;
    }
}
