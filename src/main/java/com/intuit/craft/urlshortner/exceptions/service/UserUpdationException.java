package com.intuit.craft.urlshortner.exceptions.service;

import com.intuit.craft.urlshortner.exceptions.ServiceException;
import org.springframework.http.HttpStatus;

public class UserUpdationException extends ServiceException {
    public UserUpdationException(String message, String... info) {
        super(message, info);
    }

    public UserUpdationException(String message, Exception exception, String... info) {
        super(message, exception, info);
    }

    public UserUpdationException(String message, HttpStatus httpStatus, String... info) {
        super(message, httpStatus, info);
    }

    public UserUpdationException(String message, Exception exception, HttpStatus httpStatus, String... info) {
        super(message, exception, httpStatus, info);
    }
}
