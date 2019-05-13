package com.epam.test.converter;

import java.util.List;

import com.epam.test.entity.AbstractEntity;

public interface ConverterToEntity<E extends AbstractEntity, Dto> {

    E toEntity(Dto dto);

    List<E> toEntityList(List<Dto> dtoList);
}
