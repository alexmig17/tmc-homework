package com.epam.test.converter;

import org.mapstruct.Mapper;

import com.epam.test.dto.DocumentDto;
import com.epam.test.entity.Document;

@Mapper(componentModel = "spring")
public interface DocumentConverter extends Converter<Document, DocumentDto> {

}
