package com.epam.test.controller.api;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.epam.test.dto.BookDto;
import com.epam.test.service.BookService;
import com.epam.test.service.DocumentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookApiController {

    private final BookService bookService;
    private final DocumentService documentService;

    @GetMapping
    public Page<BookDto> getBook(Pageable pageable) {
        return bookService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public BookDto getBook(@PathVariable Integer id) {
        return bookService.get(id);
    }

    @PostMapping
    public BookDto addBook(@Valid @RequestBody BookDto book) {
        return bookService.update(book);
    }

    @PostMapping("document")
    public Integer addBookDocument(@RequestParam MultipartFile file) {

        return documentService.update(file).getId();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id) {
        bookService.delete(id);
    }
}
