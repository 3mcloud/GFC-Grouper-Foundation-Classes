package com.mmm.his.cer.foundation.model;

/**
 * Holds the POA values
 *
 * @author Tim Gallagher
 * @author Nicholas DiMucci
 */
public enum GfcPoa implements GfcEnum {
  // Match the known, and acceptable, POA values per GPS documentation.
  Y(
      'Y',
      "Yes"),
  N(
      'N',
      "No"),
  U(
      'U',
      "Insufficient documnetation to determine if present on admission"),
  W(
      'W',
      "Clinically unable to determine at time of admission"),
  ONE(
      '1',
      "Exempt from POA reporting/ Unreported/ Not Used"),
  E(
      'E',
      "Exempt from POA reporting/ Unreported/ Not Used"),
  BLANK(
      ' ',
      "Exempt from POA reporting/ Unreported/ Not Used"),

  /**
   * An indicator for any invalid POA flag value.<br />
   * Uses the ASCII decimal value of 0 (NUL char).
   */
  INVALID(
      (char) 0,
      "Invalid");

  private final char charVal;
  private final String desc;

  GfcPoa(char charVal, String desc) {
    this.charVal = charVal;
    this.desc = desc;
  }

  @Override
  public int intValue() {
    return ordinal();
  }

  @Override
  public char charValue() {
    return this.charVal;
  }

  @Override
  public String getDescription() {
    return this.desc;
  }

  /**
   * @param charVal      the <code>char</code> value to attempt to parse into a known enum value.
   * @param defaultValue the default enum value to use <strong>if, and only if, the input
   *                     <code>char</code> could not be parsed</strong>.
   * @return the POA flag as an enum value if it could be parsed, else <code>defaultValue</code>
   */
  public static GfcPoa fromChar(char charVal, GfcPoa defaultValue) {
    charVal = Character.toUpperCase(charVal);

    for (GfcPoa poa : values()) {
      if (charVal == poa.charValue()) {
        return poa;
      }
    }

    return defaultValue;
  }

  /**
   * @param charVal the <code>char</code> value to attempt to parse into a known enum value.
   *                <code>E</code> will be returned for any <code>char</code> value that could not
   *                be
   *                parsed.
   * @return the POA flag as an enum value, else <code>GfcPoa.E</code> if the <code>char</code>
   *     value could not be parsed.
   * @deprecated please favor <code>public static GfcPoa fromChar(char charVal, GfcPoa
   *     defaultValue)</code>.
   */
  @Deprecated
  public static GfcPoa fromChar(char charVal) {
    return fromChar(charVal, E);
  }
}
