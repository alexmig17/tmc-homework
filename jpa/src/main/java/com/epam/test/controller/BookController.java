package com.epam.test.controller;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.rangeClosed;

import java.util.List;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.epam.test.dto.BookDto;
import com.epam.test.service.BookService;
import com.epam.test.service.DocumentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final DocumentService documentService;

    @GetMapping
    public String getBook(Pageable pageable, Model model) {
        Page<BookDto> page = bookService.getAll(pageable);
        model.addAttribute("page", page);
        int totalPages = page.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = rangeClosed(1, totalPages)
                    .boxed()
                    .collect(toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "books";
    }

    @GetMapping("/{id}")
    public String getBook(@PathVariable Integer id, Model model) {
        BookDto book = bookService.get(id);
        model.addAttribute("book", book);
        return "book";
    }

    @GetMapping(params = "new")
    public String bookNew(@RequestParam Integer documentId, Model model) {
        model.addAttribute("book", new BookDto(documentId));
        return "bookForm";
    }

    @PostMapping
    public String addBook(@Valid BookDto book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "bookForm";
        }
        Integer bookId = bookService.update(book).getId();
        return format("redirect:/book/%d", bookId);
    }

    @PostMapping(path = "{id}", params = "delete")
    public String delete(@PathVariable Integer id) {
        bookService.delete(id);
        return "redirect:/book";
    }
}
