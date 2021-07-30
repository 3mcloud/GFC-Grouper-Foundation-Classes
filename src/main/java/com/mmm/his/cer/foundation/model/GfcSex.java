package com.mmm.his.cer.foundation.model;


/**
 * Provides standard Sex values with Unknown = 0, Male = 1 and Female = 2
 *
 * @author Tim Gallagher - 3M HIS CER
 */
public enum GfcSex implements GfcEnum {

  UNKNOWN(
      "Unknown",
      'U'),
  MALE(
      "Male",
      'M'),
  FEMALE(
      "Female",
      'F');

  private final String name;
  private final char charValue;

  GfcSex(String name, char charValue) {
    this.name = name;
    this.charValue = charValue;
  }

  @Override
  public String getDescription() {
    return this.name;
  }

  @Override
  public char charValue() {
    return this.charValue;
  }

  @Override
  public int intValue() {
    return ordinal();
  }
}
