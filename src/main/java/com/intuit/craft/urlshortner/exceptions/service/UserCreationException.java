package com.intuit.craft.urlshortner.exceptions.service;

import com.intuit.craft.urlshortner.exceptions.ServiceException;
import org.springframework.http.HttpStatus;

public class UserCreationException extends ServiceException {
    public UserCreationException(String message, String... info) {
        super(message, info);
    }

    public UserCreationException(String message, Exception exception, String... info) {
        super(message, exception, info);
    }

    public UserCreationException(String message, HttpStatus httpStatus, String... info) {
        super(message, httpStatus, info);
    }

    public UserCreationException(String message, Exception exception, HttpStatus httpStatus, String... info) {
        super(message, exception, httpStatus, info);
    }
}
