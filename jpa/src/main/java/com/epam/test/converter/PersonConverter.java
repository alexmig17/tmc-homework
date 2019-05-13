package com.epam.test.converter;

import org.mapstruct.Mapper;

import com.epam.test.dto.PersonDto;
import com.epam.test.entity.Person;

@Mapper(componentModel = "spring")
public interface PersonConverter extends Converter<Person, PersonDto> {

}
