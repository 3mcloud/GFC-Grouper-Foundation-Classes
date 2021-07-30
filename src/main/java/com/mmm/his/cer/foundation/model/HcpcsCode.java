package com.mmm.his.cer.foundation.model;

import com.mmm.his.cer.foundation.utility.GfcDate;

/**
 * HCPCS Code
 *
 * @author Jason Flores
 * @author Tim Gallagher
 */
public class HcpcsCode extends ProcedureCode implements IDescribable {

  private static final long serialVersionUID = 9208621402541194385L;


  /**
   * how many times the procedure was done, and sometimes procedures are drugs so units may equal
   * dosage too
   */
  protected int units;

  /**
   *
   */
  protected int revenueCode;

  /**
   *
   */
  protected String[] mods;

  /**
   * associated cost for the procedure, and revenue code would be the hospital revenue center that
   * the charge gets billed from. Can use this field to store paid cost in some uses but for billing
   * it would be the billed charges
   */
  protected double charge;

  /**
   * shorter description of code
   */
  protected String shortDescription;

  /**
   * standard or longer description
   */
  protected String longDescription;

  /**
   * creates a blank code with unknown/invalid date
   */
  public HcpcsCode() {
  }

  /**
   * creates code with supplied value, ensuring non-null, with unknown date, and all other values
   * null or 0
   */
  public HcpcsCode(String value) {
    super(value);
  }

  /**
   * Creates code with supplied values, ensuring non-null code value
   */
  public HcpcsCode(String value, String[] mods, int revenueCode, int units, GfcDate serviceDate,
      double charge) {
    super(value, serviceDate);
    this.mods = mods;
    this.revenueCode = revenueCode;
    this.units = units;
    this.charge = charge;
  }

  /**
   * gets the charge
   */
  public double getCharge() {
    return charge;
  }

  /**
   * gets the long description
   *
   * @return may be null
   */
  @Override
  public String getLongDescription() {
    return longDescription;
  }

  /**
   * gets the mods
   *
   * @return array may be null
   */
  public String[] getMods() {
    return mods;
  }

  /**
   * gets the revenue code
   */
  public int getRevenueCode() {
    return revenueCode;
  }

  /**
   * gets the date
   *
   * @return may be null or invalid date
   * @see ProcedureCode.getDate()
   * @deprecated
   */
  @Deprecated
  public GfcDate getServiceDate() {
    return getDate();
  }

  /**
   * gets the short description
   *
   * @return may be null
   */
  @Override
  public String getShortDescription() {
    return shortDescription;
  }

  /**
   * gets the units
   */
  public int getUnits() {
    return units;
  }

  /**
   * sets the charge
   */
  public void setCharge(double charge) {
    this.charge = charge;
  }

  /**
   * sets the long description
   */
  @Override
  public void setLongDescription(String desc) {
    this.longDescription = desc;
  }

  /**
   * sets the mods
   */
  public void setMods(String[] mods) {
    this.mods = mods;
  }

  /**
   * sets the revenue code
   */
  public void setRevenueCode(int revenueCode) {
    this.revenueCode = revenueCode;
  }

  /**
   * sets the short description
   */
  @Override
  public void setShortDescription(String desc) {
    this.shortDescription = desc;
  }

  /**
   * sets the units
   */
  public void setUnits(int units) {
    this.units = units;
  }

}
