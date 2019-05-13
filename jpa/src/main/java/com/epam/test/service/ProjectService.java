package com.epam.test.service;

import java.util.Set;

import com.epam.test.dto.ProjectDto;

public interface ProjectService extends CrudService<ProjectDto, Integer> {

    void assign(Integer projectId, Integer employeeId);

    Set<ProjectDto> getProjectsWithNotExternalEmployee();
}
