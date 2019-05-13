package com.epam.test.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserDto implements Dto {

    private Integer id;
    private String firstName;
    private String lastName;
    @NotNull
    @Email
    private String email;
    private String role;
}
