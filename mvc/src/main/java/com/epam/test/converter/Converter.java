package com.epam.test.converter;

import java.util.List;

import com.epam.test.entity.Entity;

public interface Converter<E extends Entity, Dto> {

    E toEntity(Dto dto);

    List<E> toEntityList(List<Dto> dtoList);

    Dto toDto(E entity);

    List<Dto> toDtoList(List<E> entityList);
}
