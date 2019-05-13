package com.epam.test.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PersonDto extends UserDto {

    private AddressDto address;
    private LocalDate creationDate;
}
