package com.example.linkedinSample.customValidation.checkEmail;

import com.example.linkedinSample.customValidation.checkDuplicateEmail.CheckDuplicateEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target( {ElementType.METHOD,ElementType.FIELD,ElementType.LOCAL_VARIABLE, ElementType.PARAMETER, ElementType.TYPE_USE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CheckEmailValidator.class)
public @interface CheckEmail {
    //error message
    public String message() default "Email should contain atleast one @ and dot";
    //represents group of constraints
    public Class<?>[] groups() default {};
    //represents additional information about annotation
    public Class<? extends Payload>[] payload() default {};
}
