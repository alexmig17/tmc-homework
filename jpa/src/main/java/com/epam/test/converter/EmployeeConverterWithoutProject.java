package com.epam.test.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.epam.test.dto.EmployeeDto;
import com.epam.test.entity.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeConverterWithoutProject extends Converter<Employee, EmployeeDto> {

    @Mappings({
            @Mapping(target = "projects", ignore = true)
    })
    @Override
    EmployeeDto toDto(Employee entity);
}
