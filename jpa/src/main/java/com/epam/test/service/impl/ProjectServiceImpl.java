package com.epam.test.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.epam.test.converter.EmployeeConverterWithoutProject;
import com.epam.test.converter.ProjectConverter;
import com.epam.test.dto.EmployeeDto;
import com.epam.test.dto.ProjectDto;
import com.epam.test.entity.Employee;
import com.epam.test.entity.Project;
import com.epam.test.exception.NotFoundException;
import com.epam.test.repository.EmployeeRepository;
import com.epam.test.repository.ProjectRepository;
import com.epam.test.service.ProjectService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectConverter projectConverter;
    private final EmployeeConverterWithoutProject employeeConverter;

    @Override
    public ProjectDto update(ProjectDto dto) {
        Project project = projectConverter.toEntity(dto);
        return projectConverter.toDto(projectRepository.save(project));
    }

    @Override
    public void delete(Integer id) {
        projectRepository.deleteById(id);
    }

    @Override
    public ProjectDto get(Integer id) {
        return projectConverter.toDto(projectRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public Page<ProjectDto> getAll(Pageable pageable) {
        return projectRepository.findAll(pageable).map(projectConverter::toDto);
    }

    @Override
    public void assign(Integer projectId, Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(NotFoundException::new);
        Project project = projectRepository.findById(projectId).orElseThrow(NotFoundException::new);
        employee.getProjects().add(project);
        employeeRepository.save(employee);
    }

    @Override
    public Set<ProjectDto> getProjectsWithNotExternalEmployee() {
        Map<Integer, ProjectDto> projectMap = new HashMap<>();
        employeeRepository.findByExternalFalse().forEach(employee -> employee.getProjects().forEach(project -> {
            ProjectDto projectDto = projectMap.get(project.getId());
            if (projectDto == null) {
                projectDto = projectConverter.toDto(project);
                projectMap.put(project.getId(), projectDto);
            }
            Set<EmployeeDto> employees = projectDto.getEmployees();
            if (employees == null) {
                employees = new HashSet<>();
                projectDto.setEmployees(employees);
            }
            employees.add(employeeConverter.toDto(employee));
        }));
        return new HashSet<>(projectMap.values());
    }
}
