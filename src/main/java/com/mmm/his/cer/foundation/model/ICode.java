package com.mmm.his.cer.foundation.model;

/**
 * Provides a generic code
 *
 * @author Jason Flores
 * @author Tim Gallagher
 */
public interface ICode {

  /**
   * Sets the code value
   */
  void setValue(String value);

  /**
   * Gets the code value; since this can be extended, there is no guarantee that the code value not
   * null.
   *
   * @return code value, could be null
   */
  String getValue();

}
