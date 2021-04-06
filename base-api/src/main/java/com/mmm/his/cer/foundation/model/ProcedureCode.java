package com.mmm.his.cer.foundation.model;

import com.mmm.his.cer.foundation.utility.GfcDate;

/**
 * A dated procedure code
 *
 * @author Tim Gallagher
 */
public class ProcedureCode extends Code {
  private static final long serialVersionUID = -67025976451453528L;


  /**
   * The date code is used, this will be null by default, but if you access through getDate() it
   * will be reset to an "unknown" date object.
   */
  protected GfcDate date;

  /**
   * Creates blank code with an "unknown/invalid" GfcDate. The date can be reset by adjusting its
   * day, month and year values
   */
  public ProcedureCode() {}

  /**
   * Creates with code value, ensuring a non-null value with an "unknown" GfcDate
   *
   * @param value
   */
  public ProcedureCode(String value) {
    super(value);
  }

  /**
   * Creates with code value, ensuring a non-null value with the supplied date
   *
   * @param codeValue
   * @param serviceDate
   */
  public ProcedureCode(String codeValue, GfcDate serviceDate) {
    super(codeValue);
    this.date = serviceDate != null ? serviceDate : GfcDate.unknown();
  }

  /**
   * Sets the date; if the code is null, sets it to "unknown"
   *
   * @param date
   */
  public void setDate(GfcDate date) {
    this.date = date;
  }

  /**
   * gets the date
   *
   * @return this is guaranteed to be non-null, even if the date was set to null through the
   *         setDate(), directly through extends or other method
   */
  public GfcDate getDate() {
    if (this.date == null) {
      this.date = GfcDate.unknown();
    }
    return date;
  }
}
