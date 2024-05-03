package com.intuit.craft.urlshortner.exceptions;

import org.springframework.http.HttpStatus;

public class ServiceException extends BaseException {

  public ServiceException(String message, String... info) {
    super(message, info);
  }

  public ServiceException(String message, Exception exception, String... info) {
    super(message, exception, info);
  }

  public ServiceException(String message, HttpStatus httpStatus, String... info) {
    super(message, httpStatus, info);
  }

  public ServiceException(String message, Exception exception, HttpStatus httpStatus,
      String... info) {
    super(message, exception, httpStatus, info);
  }
}
