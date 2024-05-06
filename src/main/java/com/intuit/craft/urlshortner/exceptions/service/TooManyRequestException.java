package com.intuit.craft.urlshortner.exceptions.service;

import com.intuit.craft.urlshortner.exceptions.ServiceException;
import org.springframework.http.HttpStatus;

public class TooManyRequestException extends ServiceException {

    public TooManyRequestException(String message, String... info) {
        super(message, info);
    }

    public TooManyRequestException(String message, Exception exception, String... info) {
        super(message, exception, info);
    }

    public TooManyRequestException(String message, HttpStatus httpStatus, String... info) {
        super(message, httpStatus, info);
    }

    public TooManyRequestException(String message, Exception exception, HttpStatus httpStatus, String... info) {
        super(message, exception, httpStatus, info);
    }
}
