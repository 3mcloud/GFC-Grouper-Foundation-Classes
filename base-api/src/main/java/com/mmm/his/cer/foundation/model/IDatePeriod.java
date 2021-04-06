package com.mmm.his.cer.foundation.model;

import com.mmm.his.cer.foundation.utility.GfcDate;

import java.io.Serializable;

/**
 * Provides two dates that cover a period of days/years and is used to associate those dates with
 * specific date, such as a doctor visit with Diagnosis and Procedure codes.
 *
 * @author Tim Gallagher - 3M HIS C&amp;ER
 * @author Jason Flores - 3M HIS C&amp;ER
 */
public interface IDatePeriod extends Serializable {

  /**
   * gets the date for the end of care, such as discharge date, info complete (home health)
   *
   * @return end date instance
   */
  GfcDate getEndDate();

  /**
   * gets the date for the start of care, such as admit date
   *
   * @return start date instance
   */
  GfcDate getStartDate();

  /**
   * sets the Calendar end date
   *
   * @param date instance
   */
  void setEndDate(GfcDate date);

  /**
   * sets the Calendar for start date
   *
   * @param date instance
   */
  void setStartDate(GfcDate date);
}
