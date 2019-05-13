package com.epam.test.dto;

import java.util.Set;

import lombok.Data;

@Data
public class ProjectDto implements Dto {

    private Integer id;
    private String name;
    private String owner;
    private Set<EmployeeDto> employees;
}
