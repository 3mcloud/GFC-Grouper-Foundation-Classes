package com.mmm.his.cer.foundation.exception;

/**
 * The base GFC exception.<br />
 * <p>
 * Created by mfunaro Date: 8/15/2014
 */
public class FoundationException extends Exception {

  private static final long serialVersionUID = 1L;

  public FoundationException() {
  }

  public FoundationException(String message) {
    super(message);
  }

  public FoundationException(String message, Throwable cause) {
    super(message, cause);
  }

  public FoundationException(Throwable cause) {
    super(cause);
  }
}
