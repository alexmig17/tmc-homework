package com.epam.test.service;

import org.springframework.web.multipart.MultipartFile;

import com.epam.test.dto.DocumentDto;

public interface DocumentService extends CrudService<DocumentDto, Integer> {

    DocumentDto update(MultipartFile file);
}
