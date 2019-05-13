package com.epam.test.converter;

import org.mapstruct.Mapper;

import com.epam.test.dto.UnitDto;
import com.epam.test.entity.Unit;

@Mapper(componentModel = "spring")
public interface UnitConverter extends Converter<Unit, UnitDto> {

}
