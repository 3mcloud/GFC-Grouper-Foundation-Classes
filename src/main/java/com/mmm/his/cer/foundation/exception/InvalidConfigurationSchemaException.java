/**
 * (c) 3M Company 2014. Created in 2014 as an unpublished copyrighted work. This program contains
 * confidential proprietary information of 3M. Such information may not be used or reproduced
 * without prior written consent of 3M.
 */
package com.mmm.his.cer.foundation.exception;

/**
 * Created by mfunaro Date: 4/14/14 Time: 1:44 PM
 */
@Deprecated
public class InvalidConfigurationSchemaException extends Exception {

  private static final long serialVersionUID = 5070667692455517923L;

  public InvalidConfigurationSchemaException(String message) {
    super(message);
  }

}
