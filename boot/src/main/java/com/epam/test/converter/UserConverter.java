package com.epam.test.converter;

import org.mapstruct.Mapper;

import com.epam.test.dto.UserDto;
import com.epam.test.entity.User;

@Mapper(componentModel = "spring")
public interface UserConverter extends Converter<User, UserDto> {

}
