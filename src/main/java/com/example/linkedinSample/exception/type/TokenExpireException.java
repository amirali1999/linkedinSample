package com.example.linkedinSample.exception.type;

public class TokenExpireException extends Exception{
    private String message;
    public TokenExpireException(){
    }
    public TokenExpireException(String message){
        super(message);
        this.message = message;
    }
}
