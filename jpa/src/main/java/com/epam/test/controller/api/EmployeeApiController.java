package com.epam.test.controller.api;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.test.dto.EmployeeDto;
import com.epam.test.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/employee")
public class EmployeeApiController {

    private final EmployeeService employeeService;

    @GetMapping("{id}")
    public EmployeeDto getEmployById(@PathVariable Integer id) {
        return employeeService.get(id);
    }

    @GetMapping
    public Page<EmployeeDto> getAllEmploy(Pageable pageable) {
        return employeeService.getAll(pageable);
    }

    @DeleteMapping("{id}")
    public void deleteEmployById(@PathVariable Integer id) {
        employeeService.delete(id);
    }

    @PostMapping
    public EmployeeDto save(@Valid @RequestBody EmployeeDto employee) {
        return employeeService.update(employee);
    }
}
