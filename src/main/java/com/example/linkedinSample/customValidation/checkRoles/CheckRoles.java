package com.example.linkedinSample.customValidation.checkRoles;

import com.example.linkedinSample.customValidation.checkEmail.CheckEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target( {ElementType.FIELD,ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CheckRolesValidator.class)
public @interface CheckRoles {
    //error message
    public String message() default "Role not found";
    //represents group of constraints
    public Class<?>[] groups() default {};
    //represents additional information about annotation
    public Class<? extends Payload>[] payload() default {};
}
