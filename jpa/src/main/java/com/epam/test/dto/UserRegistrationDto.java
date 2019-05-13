package com.epam.test.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.epam.test.validation.PasswordsMatchingConstraint;

import lombok.Data;

@Data
@PasswordsMatchingConstraint
public class UserRegistrationDto implements Dto {

    public static final String MODEL = "userRegistrationForm";

    @NotNull
    @Size(min = 1, max = 100)
    private String firstName;
    @NotNull
    @Size(min = 1, max = 100)
    private String lastName;
    @Email
    private String email;
    @NotNull
    @Size(min = 4, max = 100)
    private String password;
    @NotNull
    @Size(min = 4, max = 100)
    private String matchingPassword;
}
