package com.example.linkedinSample.exception.type;

public class SkillWasDeletedException extends Exception{
    private String message;
    public SkillWasDeletedException(){
    }
    public SkillWasDeletedException(String message){
        super(message);
        this.message = message;
    }
}
