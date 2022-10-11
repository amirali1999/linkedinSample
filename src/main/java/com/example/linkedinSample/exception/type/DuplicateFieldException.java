package com.example.linkedinSample.exception.type;

public class DuplicateFieldException extends Exception{
    private String message;
    public DuplicateFieldException(){
    }
    public DuplicateFieldException(String message){
        super(message);
        this.message = message;
    }
}
