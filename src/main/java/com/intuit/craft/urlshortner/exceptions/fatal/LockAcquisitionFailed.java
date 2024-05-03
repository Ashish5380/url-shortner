package com.intuit.craft.urlshortner.exceptions.fatal;

public class LockAcquisitionFailed extends RuntimeException {

  public LockAcquisitionFailed(String message) {
    super(message);
  }

  public LockAcquisitionFailed(String message, Throwable cause) {
    super(message, cause);
  }
}
