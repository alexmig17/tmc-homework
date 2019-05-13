package com.epam.test.converter;

import java.util.List;

import com.epam.test.entity.AbstractEntity;

public interface ConverterToDto<E extends AbstractEntity, Dto> {

    Dto toDto(E entity);

    List<Dto> toDtoList(List<E> entityList);
}
