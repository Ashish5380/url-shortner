package com.intuit.craft.urlshortner.exceptions.fatal;

import com.intuit.craft.urlshortner.exceptions.FatalException;
import org.springframework.http.HttpStatus;

/**
 * @author sridharswain
 */
public class CacheOperationException extends FatalException {

    private String key;

    public CacheOperationException(String message, String key) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
        this.key = key;
    }

    public CacheOperationException(String message, Exception cause, String key) {
        super(message, cause, HttpStatus.INTERNAL_SERVER_ERROR);
        this.key = key;
    }
}
