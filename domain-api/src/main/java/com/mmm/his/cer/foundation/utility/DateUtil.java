package com.mmm.his.cer.foundation.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This might be worth refactoring entirely
 *
 */
public class DateUtil {

  public static final int UNSET_INT = -1;

  /**
   * @deprecated Why is this even here?
   */
  @Deprecated
  public static final String NEW_LINE = System.getProperty("line.separator");

  private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

  private DateUtil() {
    // Set to private to hide constructor
  }

  /**
   * Returns <strong>null</strong> should the date fail to parse. The default date format is set to
   * DD/MM/YYYY unless explicitly modified via
   *
   * @param date string
   * @return date object
   */
  public static Date asDate(String date) {
    try {
      return dateFormat.parse(date);
    } catch (ParseException exc) {
      return null;
    }
  }

  public static void setDateFormat(String format) {
    dateFormat = new SimpleDateFormat(format);
  }

  public static final int calculateElapsedDays(String startDate, String endDate) {
    try {
      Date start = dateFormat.parse(startDate);
      Date end = dateFormat.parse(endDate);

      // millisecond conversion
      long different = end.getTime() - start.getTime();
      long daysInMilli = (long) 1000 * 60 * 60 * 24;

      // TODO return LONG and do not reduce to INT
      return (int) (different / daysInMilli);
    } catch (ParseException exc) {
      // TODO handle or pass upwards. What should a caller do with a MIN_VALUE?
      return Integer.MIN_VALUE;
    }
  }

  public static boolean isDateBetween(String startDate, String endDate, String testDate) {
    // This date is blank, so its not between the two dates.
    if (testDate == null || testDate.equals("")) {
      return false;
    }

    try {
      Date start = dateFormat.parse(startDate);
      Date end = dateFormat.parse(endDate);
      Date test = dateFormat.parse(testDate);

      // If the test date is between the start and end dates
      if (test.compareTo(start) >= 0 && test.compareTo(end) <= 0) {
        return true;
      }
    } catch (Exception exc) {
      // TODO handle or pass upwards. Not being able to parse does not mean it is "not between".
    }

    return false;
  }

  public static int getYear(String date, int dateFormat) {
    switch (dateFormat) {
      case 1: // YYYYMMDD
        return makeInteger(date, 0, 4);
      case 2: // MMDDYYYY
      case 3: // DDMMYYYY
        return makeInteger(date, 4, 4);
    }
    return UNSET_INT;
  }

  /**
   *
   *
   * @param date
   * @param dateFormat
   * @return The month value, or {@link #UNSET_INT} if the provided <code>dateFormat</code> is not
   *         valid.
   */
  public static int getMonth(String date, int dateFormat) {
    switch (dateFormat) {
      case 1: // YYYYMMDD
        return makeInteger(date, 4, 2);
      case 3: // DDMMYYYY
        return makeInteger(date, 2, 2);
      case 2: // MMDDYYYY
        return makeInteger(date, 0, 2);
    }
    return UNSET_INT;
  }

  /**
   *
   *
   * @param date
   * @param dateFormat
   * @return The day value, or {@link #UNSET_INT} if the provided <code>dateFormat</code> is not
   *         valid.
   */
  public static int getDay(String date, int dateFormat) {
    // TODO how does one know the 'dateFormat' numbers? Use enum as parameter.
    switch (dateFormat) {
      case 1: // YYYYMMDD
        return makeInteger(date, 6, 2);
      case 2: // MMDDYYYY
        return makeInteger(date, 2, 2);
      case 3: // DDMMYYYY
        return makeInteger(date, 0, 2);
    }
    return UNSET_INT;
  }


  private static int makeInteger(String str, int off, int len) {
    int intValue = 0;
    for (int i = 0; i < len; i++) {
      int innerI = off + i;
      if (innerI >= str.length()) {
        return -1;
      }
      char intChar = str.charAt(innerI);
      if (intChar >= '0' && intChar <= '9') {
        intValue = 10 * intValue + intChar - '0';
      } else if (intChar != ' ') {
        return -1;
      }
    }
    return intValue;
  }
}
