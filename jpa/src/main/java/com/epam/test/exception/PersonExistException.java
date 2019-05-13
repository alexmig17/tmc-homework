package com.epam.test.exception;

import static java.lang.String.format;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PersonExistException extends RuntimeException {

    public PersonExistException(String email) {
        super(format("person with email already exist %s", email));
    }
}
