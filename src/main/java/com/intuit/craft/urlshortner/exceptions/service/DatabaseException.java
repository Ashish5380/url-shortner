package com.intuit.craft.urlshortner.exceptions.service;

import com.intuit.craft.urlshortner.exceptions.ServiceException;
import org.springframework.http.HttpStatus;

public class DatabaseException extends ServiceException {
    public DatabaseException(String message, String... info) {
        super(message, info);
    }

    public DatabaseException(String message, Exception exception, String... info) {
        super(message, exception, info);
    }

    public DatabaseException(String message, HttpStatus httpStatus, String... info) {
        super(message, httpStatus, info);
    }

    public DatabaseException(String message, Exception exception, HttpStatus httpStatus, String... info) {
        super(message, exception, httpStatus, info);
    }
}
