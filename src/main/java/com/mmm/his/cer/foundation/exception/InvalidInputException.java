package com.mmm.his.cer.foundation.exception;

/**
 * Created by mfunaro Date: 5/8/14
 */
@Deprecated
public class InvalidInputException extends FoundationRuntimeException {

  private static final long serialVersionUID = 5070667692455517923L;

  public InvalidInputException(String message) {
    super(message);
  }
}
