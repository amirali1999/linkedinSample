package com.example.linkedinSample.customValidation.checkDuplicateUsername;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target( {ElementType.METHOD,ElementType.FIELD,ElementType.LOCAL_VARIABLE, ElementType.PARAMETER, ElementType.TYPE_USE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CheckDuplicateUsernameValidation.class)
public @interface CheckDuplicateUsername {
    //error message
    public String message() default "Duplicate username";
    //represents group of constraints
    public Class<?>[] groups() default {};
    //represents additional information about annotation
    public Class<? extends Payload>[] payload() default {};
}
