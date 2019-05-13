package com.epam.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileUploadException extends RuntimeException {

    public FileUploadException(Throwable e) {
        super("exception during file upload", e);
    }
}
