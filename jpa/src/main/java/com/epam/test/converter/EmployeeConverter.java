package com.epam.test.converter;

import org.mapstruct.Mapper;

import com.epam.test.dto.EmployeeDto;
import com.epam.test.entity.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeConverter extends Converter<Employee, EmployeeDto> {

}
