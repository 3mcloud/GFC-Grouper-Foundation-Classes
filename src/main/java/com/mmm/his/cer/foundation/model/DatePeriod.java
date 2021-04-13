package com.mmm.his.cer.foundation.model;

import com.mmm.his.cer.foundation.utility.GfcDate;

/**
 * This provides the basic implementation of the DatePeriod, including validation to ensure the
 * start date is less than or equal to the end date.
 *
 * @author Tim Gallagher - 3M HIS C&amp;ER
 * @author a2jagzz
 */
public class DatePeriod implements IDatePeriod {
  private static final long serialVersionUID = -2779687289416687176L;

  private GfcDate startDate;
  private GfcDate endDate;

  public DatePeriod() {}

  public DatePeriod(GfcDate startDate, GfcDate endDate) {
    this.startDate = startDate;
    this.endDate = endDate;
  }

  /**
   * gets the end date
   *
   * @return end date instance or null if unset
   */
  @Override
  public GfcDate getEndDate() {
    return endDate;
  }

  /**
   * gets the start date
   *
   * @return start date instance or null if unset
   */
  @Override
  public GfcDate getStartDate() {
    return startDate;
  }

  /**
   * sets the end date
   *
   * @param endDate GfcDate instance
   */
  @Override
  public void setEndDate(GfcDate endDate) {
    this.endDate = endDate;
  }

  /**
   * sets the start date
   *
   * @param startDate GfcDate instance
   */
  @Override
  public void setStartDate(GfcDate startDate) {
    this.startDate = startDate;
  }

  /**
   * Validates that the start date is less than or equal to the end date. The quirks are that if the
   * start date is null, then the end date must be null. However, if the start date is not null, the
   * end date can be null.
   *
   * @return true if both start/end are null. True is start date is valid but there is no endDate.
   *         True if start date is valid and before the end date. False otherwise.
   */
  public boolean validate() {
    if (startDate == null) {
      /*
       * if the start date is null, then the end date must also be
       */
      return endDate == null;
    } else {
      return endDate == null ? true : startDate.compareTo(endDate) <= 0;
    }
  }

  @Override
  public String toString() {
    return "DatePeriodImpl{"
        + "startDate="
        + startDate
        + ", endDate="
        + endDate
        + '}';
  }
}
