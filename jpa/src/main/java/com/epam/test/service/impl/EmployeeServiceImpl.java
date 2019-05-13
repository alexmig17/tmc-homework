package com.epam.test.service.impl;

import static java.util.Objects.isNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.epam.test.converter.EmployeeConverter;
import com.epam.test.dto.EmployeeDto;
import com.epam.test.entity.Employee;
import com.epam.test.exception.NotFoundException;
import com.epam.test.repository.EmployeeRepository;
import com.epam.test.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeConverter employeeConverter;

    @Override
    public EmployeeDto update(EmployeeDto dto) {
        Employee employee = employeeConverter.toEntity(dto);
        if (isNull(employee.getId())) {
            employee.setPassword("123");
            employee.setRole("EMPLOYEE_ROLE");
        } else {
            employeeRepository.findById(employee.getId());
        }
        return employeeConverter.toDto(employeeRepository.save(employee));
    }

    @Override
    public void delete(Integer id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public EmployeeDto get(Integer id) {
        return employeeConverter.toDto(employeeRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public Page<EmployeeDto> getAll(Pageable pageable) {
        return employeeRepository.findAll(pageable).map(employeeConverter::toDto);
    }
}
