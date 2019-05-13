package com.epam.test.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PersonalInfoDto {

    private LocalDate birthDate;
    private String workCity;
    private String workAddress;
    private String flor;
    private String office;
    private String place;
    private String title;
}

