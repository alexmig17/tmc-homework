package com.epam.test.dto;

import java.util.Set;

import com.epam.test.enums.EmployeeStatus;

import lombok.Data;

@Data
public class EmployeeDto extends UserDto {

    private AddressDto address;
    private Boolean external;
    private EmployeeStatus status;
    private PersonalInfoDto personalInfo;
    private Set<ProjectDto> projects;
}
