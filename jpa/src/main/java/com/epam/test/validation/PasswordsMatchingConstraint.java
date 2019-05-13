package com.epam.test.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import com.epam.test.dto.UserRegistrationDto;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordsEqualConstraintValidator.class)
public @interface PasswordsMatchingConstraint {

    String message() default "password mismatch";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

class PasswordsEqualConstraintValidator implements
        ConstraintValidator<PasswordsMatchingConstraint, UserRegistrationDto> {

    @Override
    public void initialize(PasswordsMatchingConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(UserRegistrationDto user, ConstraintValidatorContext constraintValidatorContext) {
        return user.getPassword().equals(user.getMatchingPassword());
    }
}



