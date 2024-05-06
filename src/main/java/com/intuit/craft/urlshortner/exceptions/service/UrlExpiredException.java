package com.intuit.craft.urlshortner.exceptions.service;

import com.intuit.craft.urlshortner.exceptions.ServiceException;
import org.springframework.http.HttpStatus;

public class UrlExpiredException extends ServiceException {

    public UrlExpiredException(String message, String... info) {
        super(message, info);
    }

    public UrlExpiredException(String message, Exception exception, String... info) {
        super(message, exception, info);
    }

    public UrlExpiredException(String message, HttpStatus httpStatus, String... info) {
        super(message, httpStatus, info);
    }

    public UrlExpiredException(String message, Exception exception, HttpStatus httpStatus, String... info) {
        super(message, exception, httpStatus, info);
    }
}
