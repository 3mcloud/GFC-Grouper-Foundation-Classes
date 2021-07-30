package com.mmm.his.cer.foundation.exception;

/**
 * The base GFC runtime exception.<br />
 * <p>
 * Created by mfunaro Date: 8/15/2014
 */
public class FoundationRuntimeException extends RuntimeException {

  private static final long serialVersionUID = 7777457793039777977L;

  public FoundationRuntimeException() {
  }

  public FoundationRuntimeException(String message) {
    super(message);
  }

  public FoundationRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  public FoundationRuntimeException(Throwable cause) {
    super(cause);
  }
}

