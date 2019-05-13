package com.epam.test.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.epam.test.converter.BookConverter;
import com.epam.test.dto.BookDto;
import com.epam.test.entity.Book;
import com.epam.test.entity.Document;
import com.epam.test.exception.NotFoundException;
import com.epam.test.repository.BookRepository;
import com.epam.test.repository.DocumentRepository;
import com.epam.test.service.BookService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final DocumentRepository documentRepository;
    private final BookConverter bookConverter;

    @Override
    public BookDto update(BookDto dto) {
        Book book = bookConverter.toEntity(dto);
        if (book.getId() != null) {
            bookRepository.findById(book.getId());
        }
        if (dto.getDocumentId() != null) {
            Document document = documentRepository.findById(dto.getDocumentId()).orElseThrow(NotFoundException::new);
            book.setDocument(document);
        }
        return bookConverter.toDto(bookRepository.save(book));
    }

    @Override
    public void delete(Integer id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto get(Integer id) {
        return bookConverter.toDto(bookRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public Page<BookDto> getAll(Pageable pageable) {
        return bookRepository.findAll(pageable).map(bookConverter::toDto);
    }
}
