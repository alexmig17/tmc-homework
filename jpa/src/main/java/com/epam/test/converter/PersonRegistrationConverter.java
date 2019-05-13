package com.epam.test.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.epam.test.dto.UserRegistrationDto;
import com.epam.test.entity.Person;

@Mapper(componentModel = "spring")
public abstract class PersonRegistrationConverter implements ConverterToEntity<Person, UserRegistrationDto> {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Mappings({
            @Mapping(target = "role", constant = "user"),
            @Mapping(target = "password", source = "password", qualifiedByName = "encrypt"),
    })
    @Override
    public abstract Person toEntity(UserRegistrationDto user);

    @Named("encrypt")
    public String encrypt(String password) {
        return passwordEncoder.encode(password);
    }
}
