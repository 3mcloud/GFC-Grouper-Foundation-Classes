package com.mmm.his.cer.foundation.model;

import java.io.Serializable;

/**
 * Class defining the possible age-criteria fields used in component development
 *
 * @author Jason Flores
 */
public class AgeCriteria implements Serializable {

  private static final long serialVersionUID = 1L;

  protected int ageYears;
  protected int ageDaysAdmit;
  protected int ageDaysDischarge;

  public AgeCriteria() {
  }

  public AgeCriteria(int ageYears, int ageDaysAdmit, int ageDaysDischarge) {
    this.ageYears = ageYears;
    this.ageDaysAdmit = ageDaysAdmit;
    this.ageDaysDischarge = ageDaysDischarge;
  }

  public int getAgeDaysAdmit() {
    return ageDaysAdmit;
  }

  public int getAgeDaysDischarge() {
    return ageDaysDischarge;
  }

  public int getAgeYears() {
    return ageYears;
  }

  public void setAgeDaysAdmit(int ageDaysAdmit) {
    this.ageDaysAdmit = ageDaysAdmit;
  }

  public void setAgeDaysDischarge(int ageDaysDischarge) {
    this.ageDaysDischarge = ageDaysDischarge;
  }

  public void setAgeYears(int ageYears) {
    this.ageYears = ageYears;
  }
}
