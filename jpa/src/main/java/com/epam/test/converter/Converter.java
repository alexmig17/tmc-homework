package com.epam.test.converter;

import java.util.List;

public interface Converter<E, Dto> {

    E toEntity(Dto dto);

    List<E> toEntityList(List<Dto> dtoList);

    Dto toDto(E entity);

    List<Dto> toDtoList(List<E> entityList);
}
