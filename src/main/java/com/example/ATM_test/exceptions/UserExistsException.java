package com.example.ATM_test.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserExistsException extends AtmException {
    public UserExistsException(String message) {
        super(message);
    }
}
