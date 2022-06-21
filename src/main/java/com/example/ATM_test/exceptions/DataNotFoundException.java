package com.example.ATM_test.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DataNotFoundException extends AtmException {

    public DataNotFoundException(String message) {
        super(message);
    }
}
