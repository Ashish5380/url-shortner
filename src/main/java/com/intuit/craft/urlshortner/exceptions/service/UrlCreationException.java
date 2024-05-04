package com.intuit.craft.urlshortner.exceptions.service;

import com.intuit.craft.urlshortner.exceptions.ServiceException;
import org.springframework.http.HttpStatus;

public class UrlCreationException extends ServiceException {
    public UrlCreationException(String message, String... info) {
        super(message, info);
    }

    public UrlCreationException(String message, Exception exception, String... info) {
        super(message, exception, info);
    }

    public UrlCreationException(String message, HttpStatus httpStatus, String... info) {
        super(message, httpStatus, info);
    }

    public UrlCreationException(String message, Exception exception, HttpStatus httpStatus, String... info) {
        super(message, exception, httpStatus, info);
    }
}
