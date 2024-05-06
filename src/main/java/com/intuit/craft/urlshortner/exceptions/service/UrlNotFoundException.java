package com.intuit.craft.urlshortner.exceptions.service;

import com.intuit.craft.urlshortner.exceptions.ServiceException;
import org.springframework.http.HttpStatus;

public class UrlNotFoundException extends ServiceException {
    public UrlNotFoundException(String message, String... info) {
        super(message, info);
    }

    public UrlNotFoundException(String message, Exception exception, String... info) {
        super(message, exception, info);
    }

    public UrlNotFoundException(String message, HttpStatus httpStatus, String... info) {
        super(message, httpStatus, info);
    }
}
