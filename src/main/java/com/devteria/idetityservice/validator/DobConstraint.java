package com.devteria.idetityservice.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD}) // chi validation cho 1 bien
@Retention(RetentionPolicy.RUNTIME) // annotaion dc xu ly khi runtime
@Constraint(validatedBy = {DobValidator.class})
// lop nay moi chi la khai bao thoi
public @interface DobConstraint {

    String message() default "Invalid date of birth";

//    khai bao gia tri toi thieu la bao nhieu
    int min();


//    nay mac dinh cua annotation
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};



}
