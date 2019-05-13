package com.epam.test.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.epam.test.dto.UserRegistrationFormDto;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordsEqualConstraintValidator.class)
public @interface PasswordsMatchingConstraint {

}

class PasswordsEqualConstraintValidator implements
        ConstraintValidator<PasswordsMatchingConstraint, UserRegistrationFormDto> {

    @Override
    public void initialize(PasswordsMatchingConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(UserRegistrationFormDto user, ConstraintValidatorContext constraintValidatorContext) {
        return user.getPassword().equals(user.getMatchingPassword());
    }
}



