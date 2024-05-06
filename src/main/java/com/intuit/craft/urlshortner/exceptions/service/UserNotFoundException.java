package com.intuit.craft.urlshortner.exceptions.service;

import com.intuit.craft.urlshortner.exceptions.ServiceException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ServiceException {
    public UserNotFoundException(String message, String... info) {
        super(message, info);
    }

    public UserNotFoundException(String message, Exception exception, String... info) {
        super(message, exception, info);
    }

    public UserNotFoundException(String message, HttpStatus httpStatus, String... info) {
        super(message, httpStatus, info);
    }
}
