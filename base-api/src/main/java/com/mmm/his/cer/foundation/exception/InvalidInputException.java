/**
 * (c) 3M Company 2014. Created in 2014 as an unpublished copyrighted work. This program contains
 * confidential proprietary information of 3M. Such information may not be used or reproduced
 * without prior written consent of 3M.
 */
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
