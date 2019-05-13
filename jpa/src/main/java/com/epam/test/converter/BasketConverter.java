package com.epam.test.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.epam.test.converter.field.BytesToBase64;
import com.epam.test.dto.BasketDto;
import com.epam.test.entity.Basket;

@Mapper(componentModel = "spring")
public interface BasketConverter extends Converter<Basket, BasketDto>, BytesToBase64 {

    @Mappings({
            @Mapping(target = "personId", source = "entity.person.id")
    })
    @Override
    BasketDto toDto(Basket entity);
}
