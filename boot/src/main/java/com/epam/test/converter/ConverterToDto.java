package com.epam.test.converter;

import java.util.List;

import com.epam.test.entity.Entity;

public interface ConverterToDto<E extends Entity, Dto> {

    Dto toDto(E entity);

    List<Dto> toDtoList(List<E> entityList);
}
