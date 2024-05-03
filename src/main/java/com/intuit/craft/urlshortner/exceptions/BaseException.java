package com.intuit.craft.urlshortner.exceptions;

import lombok.Getter;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;

import java.util.Objects;

@Getter
public abstract class BaseException extends RuntimeException {

  private final String[] info;
  private Exception exception;
  private HttpStatus httpStatus;

  public BaseException(String message, String... info) {
    super(message);
    this.info = info;
  }

  public BaseException(String message, Exception exception, String... info) {
    this(message, info);
    this.exception = exception;
  }

  public BaseException(String message, HttpStatus httpStatus, String... info) {
    this(message, info);
    this.httpStatus = httpStatus;
  }

  public BaseException(String message, Exception exception, HttpStatus httpStatus, String... info) {
    this(message, exception, info);
    this.httpStatus = httpStatus;
  }

  public String gatherStackTrace() {
    Throwable rootException = Objects.isNull(this.exception) ? this : this.exception;

    String trace = ExceptionUtils.getStackTrace(rootException);
    return trace.length() > 7000
        ? trace.substring(0, 7000)
        : trace;
  }
}
