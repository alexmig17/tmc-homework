package com.epam.test.dto;

import java.util.List;

import lombok.Data;

@Data
public class UnitDto implements Dto {

    private Integer id;
    private String name;
    private List<EmployeeDto> employees;
}
