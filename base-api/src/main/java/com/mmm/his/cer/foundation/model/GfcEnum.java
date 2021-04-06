package com.mmm.his.cer.foundation.model;

/**
 * Defines single method to get something as an int, modeled after Number.intValue()
 *
 * @author Tim Gallagher - 3M HIS CER
 */
public interface GfcEnum {

  /**
   * gets the int value of the object, modeled after Number.intValue()
   *
   * @return int
   */
  int intValue();

  /**
   * gets the single character representation of the object. modeled after Character.intValue()
   *
   * @return non-null char
   */
  char charValue();

  /**
   * gets the description of the enum
   *
   * @return non-null String
   */
  String getDescription();

}
