package com.epam.test.controller.api;

import java.util.Set;
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

import com.epam.test.dto.IdDto;
import com.epam.test.dto.ProjectDto;
import com.epam.test.service.ProjectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/project")
public class ProjectApiController {

    private final ProjectService projectService;

    @GetMapping("{id}")
    public ProjectDto getById(@PathVariable Integer id) {
        return projectService.get(id);
    }

    @GetMapping
    public Page<ProjectDto> getAll(Pageable pageable) {
        return projectService.getAll(pageable);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id) {
        projectService.delete(id);
    }

    @PostMapping
    public ProjectDto save(@Valid @RequestBody ProjectDto project) {
        return projectService.update(project);
    }

    @PostMapping("/{projectId}/employee")
    public void assign(@PathVariable Integer projectId, @RequestBody IdDto id) {
        projectService.assign(projectId, id.getId());
    }

    @GetMapping(params = "external=false")
    public Set<ProjectDto> getAll() {
        return projectService.getProjectsWithNotExternalEmployee();
    }
}
