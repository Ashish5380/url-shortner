package com.intuit.craft.urlshortner.exceptions;

import org.springframework.http.HttpStatus;

public class FatalException extends BaseException {

  public FatalException(String message, String... info) {
    super(message, info);
  }

  public FatalException(String message, Exception exception, String... info) {
    super(message, exception, info);
  }

  public FatalException(String message, HttpStatus httpStatus, String... info) {
    super(message, httpStatus, info);
  }

  public FatalException(String message, Exception exception, HttpStatus httpStatus,
      String... info) {
    super(message, exception, httpStatus, info);
  }
}
