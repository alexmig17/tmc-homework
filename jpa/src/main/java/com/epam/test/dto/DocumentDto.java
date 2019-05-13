package com.epam.test.dto;

import com.epam.test.enums.DocumentType;

import lombok.Data;

@Data
public class DocumentDto implements Dto {

    private Integer id;
    private byte[] data;
    private DocumentType type;
}
