package com.epam.test.enums;

import static java.util.stream.Stream.of;

public enum DocumentType {

    EPUB, FB2, PDF;

    public static DocumentType getByName(String extension) {
        return of(values()).filter(type -> type.name().equalsIgnoreCase(extension)).findAny().orElseThrow(IllegalArgumentException::new);
    }

}
