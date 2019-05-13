package com.epam.test.converter;

import org.mapstruct.Mapper;

import com.epam.test.dto.ProjectDto;
import com.epam.test.entity.Project;

@Mapper(componentModel = "spring")
public interface ProjectConverter extends Converter<Project, ProjectDto> {

}
