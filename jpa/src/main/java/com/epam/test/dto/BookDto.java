package com.epam.test.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookDto implements Dto {

    private Integer id;
    private String title;
    private String author;
    private String description;
    private String isbn;
    private Integer documentId;

    public BookDto(Integer documentId) {
        this.documentId = documentId;
    }
}
