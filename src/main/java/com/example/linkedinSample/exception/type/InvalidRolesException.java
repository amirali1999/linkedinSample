package com.example.linkedinSample.exception.type;

public class InvalidRolesException extends Exception {
    private String message;
    public InvalidRolesException(){
    }
    public InvalidRolesException(String message){
        super(message);
        this.message = message;
    }
}
