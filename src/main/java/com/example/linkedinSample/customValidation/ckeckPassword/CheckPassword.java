package com.example.linkedinSample.customValidation.ckeckPassword;

import com.example.linkedinSample.customValidation.checkEmail.CheckEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target( {ElementType.METHOD,ElementType.FIELD,ElementType.LOCAL_VARIABLE, ElementType.PARAMETER, ElementType.TYPE_USE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CheckPasswordValidator.class)
public @interface CheckPassword {
    //error message
    public String message() default "password must contain at least one uppercase letter, one lowercase letter and a number";
    //represents group of constraints
    public Class<?>[] groups() default {};
    //represents additional information about annotation
    public Class<? extends Payload>[] payload() default {};
}
