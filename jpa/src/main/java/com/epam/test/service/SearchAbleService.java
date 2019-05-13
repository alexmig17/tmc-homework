package com.epam.test.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.epam.test.dto.Dto;

import lombok.NonNull;

public interface SearchAbleService<T extends Dto> {

    Page<T> search(@NonNull String search, @NonNull Pageable pageable);
}
