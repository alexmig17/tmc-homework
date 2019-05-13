package com.epam.test.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.epam.test.dto.BookDto;
import com.epam.test.entity.Book;

@Mapper(componentModel = "spring")
public interface BookConverter extends Converter<Book, BookDto> {

    @Override
    @Mappings({
            @Mapping(target = "documentId", source = "entity.document.id")
    })
    BookDto toDto(Book entity);
}
