package com.epam.test.service;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.epam.test.dto.Dto;

import lombok.NonNull;

public interface CrudService<T extends Dto, I extends Serializable> {

    T update(@NonNull T dto);

    void delete(@NonNull I id);

    T get(I id);

    Page<T> getAll(@NonNull Pageable pageable);
}
