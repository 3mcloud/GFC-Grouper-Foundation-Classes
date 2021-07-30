package com.mmm.his.cer.foundation.utility;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.TimeZone;

/**
 * <p>
 * This Date object holds only Elapsed days, month, day and year information and not any time
 * oriented information in order to avoid any time oriented processing overhead that is not actually
 * required for Day oriented dates.
 * </p>
 * <p>
 * The getTime() returns the milliseconds compatible with the standard Java Date.getTime(). However,
 * there is no setTime().
 * </p>
 * <p>
 * Design Notes: Historical component processing has the following requirements for a Date:
 * <ol>
 * <li>Increase speed by removing Time elements, since they are not used by the components</li>
 * <li>Bad data pass thru – if the component does not use the bad data, don’t validate it.</li>
 * <li>Lazy date validation – no validation on constructor, which reduces processing if the date is
 * not actually used</li>
 * <li>Lazy date calculations - perform date conversions only when required and only once. For
 * example, if a process creates a String date, the String would not be converted to elapsed days or
 * year, etc. until the component needs it.</li>
 * <li>Provide empty date – a value for elapsedDays, month, day, year equal to zero</li>
 * <li>Object doesn’t modify initial date info - Data used to initialize date should be accessible
 * unchanged. For example, if the date is initialized with '20150140', then then toString() should
 * return '20150140'. However, getDay(), which requires conversion, will not reflect the value of
 * 40.</li>
 * <li>Limit Exceptions - Don’t complicate programming by requiring try/catch blocks for every
 * access to date info</li>
 * <li>Be able to determine bad dates without throwing an Exception</li>
 * <li>Do not be lenient - i.e. Dec 32 does not roll over to the next year, and Feb 29 does not roll
 * over to Mar 1 on a non-leap year.</li>
 * <li>Date creation would allow String as YYYYMMDD, integers for month, day and year, and elapsed
 * days.</li>
 * </ol>
 * </p>
 * <p>
 * Given the above requirements, its apparent that there are several basic states that a constructed
 * object can have: validated and non-validated. There are 3 different ways to create the object, so
 * this creates at least 6 different states. Further, due to the Bad data pass-thru, lazy validation
 * and calculating, an object should be able to return its construction information without change
 * or calculating. But when calculation is required it should only be done one time.
 * </p>
 * <p>
 * The GfcDate was constructor using the State pattern in order to allow the date creation with
 * String, Elapsed days, or (year, month, day) initial values, and handle the
 * validated/non-validated states without peppering the source with "if" statements trying to
 * determine object state. See <a href="https://en.wikipedia.org/wiki/State_pattern">State
 * Pattern</a>
 * </p>
 * <p>
 * The states allow the initializing data to be returned without change. When data other than the
 * initializing data (eg. String constructor but elapsed days required) is required, then the state
 * calls the conversion routines and ensures that the conversion is only called once.
 * </p>
 * <p>
 * The isValid() method was added to avoid throwing Exceptions. This method performs late validation
 * and will check for an appropriate non-lenient date without changing the original constructor
 * information. For example, if the date was created with a bad String date, isValid() returns
 * false, but the toString() will return the original bad String date. If the String date is
 * "20150140", then the string will be preserved, the elapsed days would be 0, which is a valid
 * elapsed days. However, the date object is considered invalid.
 * </p>
 * <p>
 * It is highly recommended that Components use the isValid() to ensure that elapsed days, year,
 * month and day contain valid information prior to using them to calculate.
 *
 * @author Tim Gallagher - 3M HIS CER
 * @deprecated Use {@link LocalDate} instead
 */
@Deprecated
public class GfcDate implements Serializable, Cloneable, Comparable {

  // private static final int DAYS_IN_LEAP_YEAR = 366;

  public static final int DAYS_IN_4_YEAR_CHUNK = 1461;
  public static final int BASE_YEAR = 1849;

  /*
   * an array for the offset of the day within a year per month
   */
  private static final int[] JULIAN_DAY =
      {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365, 999};
  private static final int[] JULIAN_DAY_LEAP_YEAR =
      {0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335, 366, 999};

  /**
   * The number of days between January 1, 1849 and December 31, 1899, inclusive
   */
  public static final int DAYS_BETWEEN_1849_AND_1900 = 18627;

  /**
   * The number of days from January 1, 1849 and December 31, 1899, inclusive (between 1/1/1849 and
   * 1/1/1970)
   */
  private static final int DAYS_BETWEEN_1849_AND_1970 = 44194;

  /**
   * The number of milliseconds in a day
   */
  private static final long MILLISECONDS_IN_DAY = 86400000;

  /**
   * the number of milliseconds since January 1, 1970, 00:00:00 GMT (similar to
   * <tt>java.util.Date</tt>.
   */
  private transient long javaDateMillis;

  /**
   * The internal state of the date information
   */
  private GfcDateState gfcState;

  /**
   * Constructs a blank invalid date
   */
  public GfcDate() {
    gfcState = new GfcDateValidated(0, 0, 0, 0, "", false);
  }

  /**
   * Creates an instance with year, month and day, validating them
   *
   * @param year  value
   * @param month value
   * @param day   value
   */
  public GfcDate(int year, int month, int day) {
    this.gfcState = new GfcDateMDYInitialized(month, day, year);
  }

  /**
   * This takes a default string date with the format of YYYYMMDD; if any other date format is
   * required, use the standard Java SimpleDateFormat and call the constructor that accepts a
   * java.util.Date. This will validate the year, month and day values.
   *
   * @param strDate in YYYYMMDD
   */
  public GfcDate(String strDate) {
    this.gfcState = new GfcDateStringInitialized(strDate);
  }

  /**
   * Constructing from a standard Date object pulling the year (adjusted because it starts at 1900),
   * the month (adjusted because it starts with 0 for January), and the day of the month.
   *
   * @param date object
   */
  public GfcDate(Date date) {
    this(date.getYear() + 1900, date.getMonth() + 1, date.getDate());
  }

  /**
   * Constructing from milliseconds
   *
   * @deprecated - unless the milliseconds are adjusted to GMT this may created a date that is
   *     off by 1 day from the original date.
   */
  @Deprecated
  public GfcDate(long milliseconds) {
    this.javaDateMillis = milliseconds;

    /*
     * Convert millies to Elapsed days There are 18,627 days between 1849 and 1970 There are
     * 86,400,000 milliseconds in a day
     */
    this.gfcState = new GfcDateElapsedInitialized(
        (int) (this.javaDateMillis / MILLISECONDS_IN_DAY + DAYS_BETWEEN_1849_AND_1970));
  }

  public GfcDate(int elapsedDays) {
    this.gfcState = new GfcDateElapsedInitialized(elapsedDays);
  }

  /**
   * Creates an instance used mostly for cloning this object
   *
   * @param year    integer
   * @param month   integer
   * @param day     integer
   * @param elapsed integer
   * @param strDate from string
   * @param valid   flag indicating if the data supplied reflects a valid date
   */
  protected GfcDate(int year, int month, int day, int elapsed, String strDate, boolean valid) {
    this.gfcState = new GfcDateValidated(year, month, day, elapsed, strDate, valid);
  }

  /**
   * Creates a GfcDate for today.
   *
   * @return non-null GfcDate
   */
  public static GfcDate now() {
    return new GfcDate(System.currentTimeMillis());
  }

  /**
   * Creates a GfcDate with all zero values, blank string and invalid flag; this date can be altered
   * without effecting other instances.
   */
  public static GfcDate unknown() {
    return new GfcDate(0, 0, 0, 0, "", false);
  }

  /**
   * Sets the GfcDate's state as it goes from un-validated to validated. The state should never be
   * null, but it is not checked here.
   */
  protected void setGfcState(GfcDateState gfcState) {
    this.gfcState = gfcState;
  }

  /**
   * Returns a string in the form of YYYYMMDD which allows string comparison of dates using basic
   * String.equals() or String.compareTo() and get correct results.
   *
   * @return non-null String
   */
  @Override
  public String toString() {
    return this.gfcState.toString(this);
  }

  /**
   * returns the flag indicating if this date is valid or not.
   */
  public boolean isValid() {
    return this.gfcState.isValid(this);
  }

  /**
   * Hash code based on the elapsed days
   *
   * @return integer hashcode
   */
  @Override
  public int hashCode() {
    return this.gfcState.getElapsedDays(this); // To change body of generated methods, choose Tools
    // | Templates.
  }

  /**
   * Compares to a Date
   *
   * @param anotherDate - either a GfcDate, or java.util.Date
   * @return 1 if anotherDate is null, otherwise, the difference between the two dates
   * @throws IllegalArgumentException - if not a valid comparison type
   */
  @Override
  public int compareTo(Object anotherDate) throws IllegalArgumentException {
    int diff = 0;

    if (anotherDate == null) {
      diff = 1;
    } else if (anotherDate instanceof GfcDate) {
      diff = compareTo((GfcDate) anotherDate);
    } else if (anotherDate instanceof Date) {
      diff = compareTo((Date) anotherDate);
    } else {
      throw new IllegalArgumentException("compareTo can not compare object of type: "
          + anotherDate.getClass().getName());
    }

    return diff;
  }

  /**
   * Compares to a java.util.Date using milliseconds and compensates for the fact that the Date can
   * represent a large range of milliseconds within a single day.
   *
   * @param anotherDate to compare to
   * @return 1 if anotherDate is null, otherwise, the difference between the two dates
   */
  public final int compareTo(Date anotherDate) {
    int diff;

    if (anotherDate == null) {
      diff = 1;
    } else {
      diff = (int) (getTime() - anotherDate.getTime());
      /*
       * since the Date object has a range of 0 to MILLISECONDS_IN_DAY within a day, we need to
       * determine if the difference is within that day range
       */
      if (diff < 0) {
        if (diff + MILLISECONDS_IN_DAY >= 0) {
          diff = 0;
        }
      }
    }

    return diff;
  }

  /**
   * Compares to a GfcDate using the elapsed days
   *
   * @param anotherDate to compare to
   * @return 1 if anotherDate is null, otherwise, the difference between the two dates
   */
  public final int compareTo(GfcDate anotherDate) {
    return anotherDate == null ? 1
        : this.gfcState.getElapsedDays(this) - anotherDate.getElapsedDays();
  }

  /**
   * Compares this elapsed days to the startDate and endDate elapsed days to determine if this
   * elapsed days it equal to or between the other dates
   *
   * @param startDate - start of time period
   * @param endDate   - end of time period
   * @return true if this date is equal to or between the start and end dates
   */
  public boolean containedWithin(GfcDate startDate, GfcDate endDate) {
    if (startDate == null || endDate == null) {
      return false;
    } else {
      final int myElapsedDays = this.gfcState.getElapsedDays(this);
      return myElapsedDays >= startDate.getElapsedDays()
          && myElapsedDays <= endDate.getElapsedDays();
    }
  }


  @Override
  public boolean equals(Object obj) {
    return compareTo(obj) == 0;
  }

  /**
   * Tests if this date is equal after the specified date.
   *
   * @param otherDate non-null GfcDate
   * @return true if this date is after the other date
   */
  public boolean equalsOrAfter(GfcDate otherDate) {
    return compareTo(otherDate) >= 0;
  }

  /**
   * Tests if this date is equal or before the specified date.
   *
   * @param otherDate non-null GfcDate
   * @return true if this date is after the other date
   */
  public boolean equalsOrBefore(GfcDate otherDate) {
    return compareTo(otherDate) <= 0;
  }

  /**
   * Tests if this date is after the specified date.
   *
   * @param otherDate non-null GfcDate
   * @return true if this date is after the other date
   */
  public boolean after(GfcDate otherDate) {
    return compareTo(otherDate) > 0;
  }

  /**
   * Tests if this date is before the specified date.
   *
   * @param otherDate non-null GfcDate
   * @return true if this date is after the other date
   */
  public boolean before(GfcDate otherDate) {
    return compareTo(otherDate) < 0;
  }

  /**
   * Tests if this date is after the specified date.
   *
   * @param otherDate non-null java.util.Date
   * @return true if this date is after the other date
   */
  public boolean after(Date otherDate) {
    return compareTo(otherDate) > 0;
  }

  /**
   * Tests if this date is after the specified date.
   *
   * @param otherDate non-null java.util.Date
   * @return true if this date is after the other date
   */
  public boolean before(Date otherDate) {
    return compareTo(otherDate) < 0;
  }

  /**
   * The number of Days since 1849
   *
   * @return number of days.
   */
  public int getElapsedDays() {
    return this.gfcState.getElapsedDays(this);
  }

  /**
   * The date in milliseconds since 1970 (similar to Java System.currentTimeMillis()) based on the
   * elapsed days.<br />
   * <p>
   * Update: 2015/05/01 - Tim Gallagher - added the GMT time zone adjustment in order to ensure that
   * the milliseconds returned by this method would match the milliseconds returned by
   * Date().getTime() which is time zone sensitive.
   *
   * @return long
   */
  public long getTime() {
    if (this.javaDateMillis == 0) {
      final long adjustedElapsed = this.gfcState.getElapsedDays(this) - DAYS_BETWEEN_1849_AND_1970;
      /*
       * convert the elapsed Days into milliseconds using: There are 18,627 days between 1849 and
       * 1970 There are 86,400,000 milliseconds in a day.
       */
      this.javaDateMillis = adjustedElapsed * MILLISECONDS_IN_DAY;

      /*
       * add the time zone difference between GMT and the current local so that this millisecond
       * value more closely matches the Date().getTime(). Since the getRawOffset() returns a
       * negative number for anything west of GMT, actually use "minus the negative number"
       */
      this.javaDateMillis -= TimeZone.getDefault().getRawOffset();
    }

    return this.javaDateMillis;
  }

  /**
   * Returns a java.util.Date set to the same time as this date
   *
   * @return date object
   */
  public Date getDate() {
    return new Date(getTime());
  }

  /**
   * Sets the Day of the month - Warning: this will call decodeDate which may become operationally
   * expensive. It is better to construct the date with this item.
   *
   * @param day integer
   */
  public void setDay(int day) {
    this.gfcState.setDay(this, day);
  }

  public int getDay() {
    return this.gfcState.getDay(this);
  }

  /**
   * Sets the month - Warning: this will call decodeDate which may become operationally expensive.
   * It is better to construct the date with this item.
   *
   * @param month integer
   */
  public void setMonth(int month) {
    this.gfcState.setMonth(this, month);
  }

  /**
   * gets the month associated with this date
   */
  public int getMonth() {
    return this.gfcState.getMonth(this);
  }

  /**
   * Sets the Year - Warning: this will call decodeDate which may become operationally expensive. It
   * is better to construct the date with this item.
   *
   * @param year integer
   */
  public void setYear(int year) {
    this.gfcState.setYear(this, year);
  }


  /**
   * gets the year associated with this date
   */
  public int getYear() {
    return this.gfcState.getYear(this);
  }

  @Override
  public GfcDate clone() {
    return new GfcDate(getYear(), getMonth(), getDay(), getElapsedDays(), toString(), isValid());
  }

  public final boolean isLeapYear(int year) {
    return year % 4 == 0 && year != 1900 && year != 2100 && year != 2200 && year != 2300;
  }

  /**
   * This creates a new State based on parsing the string. 1849
   *
   * @return long
   */
  private GfcDateState decodeDateAsState(String strDate) {
    int year = 0;
    int month = 0;
    int day = 0;
    int elapsedDays = 0;
    int maxDayPerMonth;
    int[] dayArray;

    if (strDate == null) {
      return new GfcDateValidated(year, month, day, elapsedDays, strDate, false);
    } else {
      // make sure the string is long enough
      strDate = strDate.trim();
      if (strDate.length() != 8) {
        return new GfcDateValidated(year, month, day, elapsedDays, strDate, false);
      } else {
        try {
          year = Integer.parseInt(strDate.substring(0, 4));

          month = Integer.parseInt(strDate.substring(4, 6));
          if (month <= 0 || month > 12) {
            return new GfcDateValidated(year, month, day, elapsedDays, strDate, false);
          }

          day = Integer.parseInt(strDate.substring(6));
          // ensure the day does not exceed the max for the month
          dayArray = isLeapYear(year) ? JULIAN_DAY_LEAP_YEAR : JULIAN_DAY;
          maxDayPerMonth = dayArray[month] - dayArray[month - 1];
          if (day <= 0 || day > maxDayPerMonth) {
            return new GfcDateValidated(year, month, day, elapsedDays, strDate, false);
          }

        } catch (NumberFormatException exc) {
          return new GfcDateValidated(year, month, day, elapsedDays, strDate, false);
        }
      }

      if (year >= BASE_YEAR) {
        int jul = dayArray[month - 1] + day;

        if (jul <= dayArray[month]) {
          elapsedDays = (year - BASE_YEAR) * DAYS_IN_4_YEAR_CHUNK / 4 + jul - 1;
          if (year > 1900) {
            elapsedDays--; // Account for the fact that 1900 not a leap year
          }
        }
      } else {
        return new GfcDateValidated(year, month, day, elapsedDays, strDate, false);
      }
    }

    return new GfcDateValidated(year, month, day, elapsedDays, strDate, true);
  }

  /**
   * This calculates the Month, Day, Year based on the elapsed days from January 1, 1849. Since this
   * is elapsed days, a date of 01/01/1849 would have an elapsed value of 0.
   */
  private GfcDateState calcMDYAsState(int elapsedDays) {
    int year;
    int month = 0;
    int day = 0;
    int remainingDays;

    /*
     * determine the year be dividing by the number days in a 4 year chunks and then multiply it by
     * 4. Then adjust for the portion of the left over chunk days
     */
    year = elapsedDays / DAYS_IN_4_YEAR_CHUNK * 4;

    /*
     * replaced "years += (remainingDays / 365)" with the following because the DAYS_IN_4_YEAR_CHUNK
     * is (365 * 4 + 1), and so the the remaining last day in that chunk (DAYS_IN_4_YEAR_CHUNK = 365
     * * 4, which would cause the divide by 365 to incorrectly rollover to the 4th year. Since the
     * elapsedDays is 0 based, we need to add on since 0 and DAYS_IN_4_YEAR_CHUNK both equal 0
     */
    remainingDays = elapsedDays % DAYS_IN_4_YEAR_CHUNK;

    if (remainingDays >= 1095) {
      year += 3;
    } else if (remainingDays >= 730) {
      year += 2;
    } else if (remainingDays >= 365) {
      ++year;
    }

    /*
     * calculate the days remaining within the actual year and not just the 4 year chunk. given the
     * year calculate the actual days and subtract that from the current elapse time in order to get
     * the left over days in the current year.
     */
    remainingDays = elapsedDays - year * DAYS_IN_4_YEAR_CHUNK / 4 + 1;

    // currently, year is since 1849, so adjust year to actual year
    year += BASE_YEAR;
    if (year > 1900) {
      remainingDays++; // Account for the fact that 1900 not a leap year
    }

    /*
     * find the month by looping backwards in the month limit array
     */
    final int[] dayArray = isLeapYear(year) ? JULIAN_DAY_LEAP_YEAR : JULIAN_DAY;
    for (int idx = dayArray.length - 1; idx >= 0; idx--) {
      /*
       * if the remainin days is greater than the current months limit, then the remaining days is
       * within the current month
       */
      if (remainingDays > dayArray[idx]) {
        month = idx + 1;
        if (month > 12) {
          month = 1;
          ++year;
        }
        day = remainingDays - dayArray[idx];
        break;
      }
    }

    return new GfcDateValidated(year, month, day, elapsedDays, null, true);
  }

  /**
   * This calculates the Elapsed Days based on Month, Day, Year based from January 1, 1849. If the
   * month or day are out of range for the year, then the Elapsed Days is 0 and the date is invalid
   */
  private GfcDateState calcElapsedAsState(int month, int day, int year) {
    int elapsedDays = 0;
    boolean valid = true;

    if (month > 0 && month <= 12) {
      // make sure the day does not exceed the max for the month
      int[] daysMax = isLeapYear(year) ? JULIAN_DAY_LEAP_YEAR : JULIAN_DAY;
      if (day <= daysMax[month] - daysMax[month - 1]) {
        int adjustedYear = year - 1849;
        elapsedDays = adjustedYear / 4 * DAYS_IN_4_YEAR_CHUNK + adjustedYear % 4 * 365;

        // if the year > 1900 then the 4 year chunk has an extra day - remove it
        if (year > 1900) {
          elapsedDays--;
        }
        elapsedDays += daysMax[month - 1] + day - 1;
      } else {
        valid = false;
      }

    } else {
      valid = false;
    }

    return new GfcDateValidated(year, month, day, elapsedDays, null, valid);
  }

  /**
   * Creates a new GfcDate that is earlier than this GfcDate
   *
   * @param days - positive int (negative int will add days. Use addDays() instead for clarity)
   * @return GfcDate implementation
   */
  public GfcDate minusDays(int days) {
    return new GfcDate(getElapsedDays() - days);
  }

  /**
   * Creates a new GfcDate that is later than this GfcDate
   *
   * @param days - positive int (negative int will subtract days. Use minusDays() instead for
   *             clarity)
   * @return GfcDate implementation
   */
  public GfcDate addDays(int days) {
    return new GfcDate(getElapsedDays() + days);
  }

  /**
   * This defines the methods that must be checked for valid state prior to providing a response.
   * These methods are allowed to change the state object within the GfcDate so that once the data
   * is validated, the new state object will not revalidate or calculate again.
   */
  interface GfcDateState extends Serializable {

    /**
     * Returns a string in the form of YYYYMMDD which allows string comparison of dates using basic
     * String.equals() or String.compareTo() and get correct results.
     *
     * @return non-null String
     */
    String toString(GfcDate gfcDate);

    /**
     * Hash code based on the elapsed time
     *
     * @return integer hashcode
     */
    int hashCode(GfcDate gfcDate);

    /**
     * Compares to a GfcDate using the elapsed days
     *
     * @param anotherDate to compare to
     * @return 1 if anotherDate is null, otherwise, the difference between the two dates
     */
    int compareTo(GfcDate gfcDate, GfcDate anotherDate);

    /**
     * The number of Days since 1849
     *
     * @return number of days.
     */
    int getElapsedDays(GfcDate gfcDate);

    /**
     * Sets the Day of the month - Warning: this will call decodeDate which may become operationally
     * expensive. It is better to construct the date with this item.
     *
     * @param day integer
     * @deprecated - use the constructor
     */
    @Deprecated
    void setDay(GfcDate gfcDate, int day);

    /**
     * Gets the day representing the day of the month
     */
    int getDay(GfcDate gfcDate);

    /**
     * Sets the month - Warning: this will call decodeDate which may become operationally expensive.
     * It is better to construct the date with this item.
     *
     * @param month integer
     * @deprecated - use the constructor
     */
    @Deprecated
    void setMonth(GfcDate gfcDate, int month);

    /**
     * Gets the month of the year
     */
    int getMonth(GfcDate gfcDate);

    /**
     * Sets the Year - Warning: this will call decodeDate which may become operationally expensive.
     * It is better to construct the date with this item.
     *
     * @param year integer
     * @deprecated - use the constructor
     */
    @Deprecated
    void setYear(GfcDate gfcDate, int year);

    /**
     * Gets the actual year, not an elapsed year.
     */
    int getYear(GfcDate gfcDate);

    boolean isValid(GfcDate gfcDate);
  }

  /**
   * This represents a fully validated date which can be in either a valid or invalid state. The
   * information within is not altered by any of the methods so that all data that goes into it,
   * comes out of it unchanged.
   */
  class GfcDateValidated implements GfcDateState {

    /**
     * The year for this date
     */
    private final int year;

    /**
     * The month for this date - this is a 1 based value, unlike the java.util.Date which is a 0
     * based value.
     */
    private final int month;

    /**
     * The day of the month
     */
    private final int day;

    /**
     * the number of days since January 1, 1849.
     */
    private final int elapsedDays;

    /**
     * Default string representation of the date as YYYYMMDD
     */
    private String strDate;

    /**
     * Flag for valid information within the date.
     */
    private final boolean valid;

    public GfcDateValidated(int year, int month, int day, int elapsedDays, String strDate,
        boolean valid) {
      this.year = year;
      this.month = month;
      this.day = day;
      this.elapsedDays = elapsedDays;
      this.strDate = strDate;
      this.valid = valid;
    }

    /**
     * Returns a string for the date as YYYYMMDD, zero padded
     */
    @Override
    public String toString(GfcDate gfcDate) {
      if (this.strDate == null) {
        this.strDate = String.format("%04d%02d%02d", this.year, this.month, this.day);
      }
      return this.strDate;
    }

    @Override
    public int hashCode(GfcDate gfcDate) {
      return elapsedDays;
    }

    @Override
    public int compareTo(GfcDate gfcDate, GfcDate anotherDate) {
      return anotherDate == null ? 1 : this.elapsedDays - anotherDate.getElapsedDays();
    }

    @Override
    public int getElapsedDays(GfcDate gfcDate) {
      return elapsedDays;
    }

    @Override
    public void setDay(GfcDate gfcDate, int day) {
      gfcDate.setGfcState(new GfcDateMDYInitialized(this.month, day, this.year));
    }

    @Override
    public int getDay(GfcDate gfcDate) {
      return day;
    }

    @Override
    public void setMonth(GfcDate gfcDate, int month) {
      gfcDate.setGfcState(new GfcDateMDYInitialized(month, this.day, this.year));
    }

    @Override
    public int getMonth(GfcDate gfcDate) {
      return month;
    }

    @Override
    public void setYear(GfcDate gfcDate, int year) {
      gfcDate.setGfcState(new GfcDateMDYInitialized(this.month, this.day, year));
    }

    @Override
    public int getYear(GfcDate gfcDate) {
      return year;
    }

    @Override
    public boolean isValid(GfcDate gfcDate) {
      return valid;
    }
  }

  /**
   * Date created as a String and elapsed days, month, day and year must be calculated when
   * requested.
   */
  class GfcDateStringInitialized implements GfcDateState {

    private final String strDate;

    public GfcDateStringInitialized(String strDate) {
      this.strDate = strDate;
    }

    @Override
    public String toString(GfcDate gfcDate) {
      return strDate;
    }

    @Override
    public int hashCode(GfcDate gfcDate) {
      return strDate == null ? 0 : strDate.hashCode();
    }

    @Override
    public int compareTo(GfcDate gfcDate, GfcDate anotherDate) {
      GfcDateState state = gfcDate.decodeDateAsState(strDate);
      gfcDate.setGfcState(state);
      return state.compareTo(gfcDate, anotherDate);
    }

    @Override
    public int getElapsedDays(GfcDate gfcDate) {
      GfcDateState state = gfcDate.decodeDateAsState(strDate);
      gfcDate.setGfcState(state);
      return state.getElapsedDays(gfcDate);
    }

    @Override
    public void setDay(GfcDate gfcDate, int day) {
      gfcDate.setGfcState(new GfcDateMDYInitialized(0, day, 0));
    }

    @Override
    public int getDay(GfcDate gfcDate) {
      GfcDateState state = gfcDate.decodeDateAsState(strDate);
      gfcDate.setGfcState(state);
      return state.getDay(gfcDate);
    }

    @Override
    public void setMonth(GfcDate gfcDate, int month) {
      gfcDate.setGfcState(new GfcDateMDYInitialized(month, 0, 0));
    }

    @Override
    public int getMonth(GfcDate gfcDate) {
      GfcDateState state = gfcDate.decodeDateAsState(strDate);
      gfcDate.setGfcState(state);
      return state.getMonth(gfcDate);
    }

    @Override
    public void setYear(GfcDate gfcDate, int year) {
      gfcDate.setGfcState(new GfcDateMDYInitialized(0, 0, year));
    }

    @Override
    public int getYear(GfcDate gfcDate) {
      GfcDateState state = gfcDate.decodeDateAsState(strDate);
      gfcDate.setGfcState(state);
      return state.getYear(gfcDate);
    }

    @Override
    public boolean isValid(GfcDate gfcDate) {
      GfcDateState state = gfcDate.decodeDateAsState(strDate);
      gfcDate.setGfcState(state);
      return state.isValid(gfcDate);
    }
  }

  /**
   * Date created with Elapsed days and the String, month, day and year must be calculated when
   * requested - this date is consider valid for any elapsed days greater than or equal to 0
   */
  class GfcDateElapsedInitialized implements GfcDateState {

    private final int elapsedDays;
    private final boolean valid;

    public GfcDateElapsedInitialized(int elapsedDays) {
      this.elapsedDays = elapsedDays;
      this.valid = this.elapsedDays >= 0;
    }

    @Override
    public String toString(GfcDate gfcDate) {
      GfcDateState state = gfcDate.calcMDYAsState(elapsedDays);
      gfcDate.setGfcState(state);
      return state.toString(gfcDate);
    }

    @Override
    public int hashCode(GfcDate gfcDate) {
      return this.elapsedDays;
    }

    @Override
    public int compareTo(GfcDate gfcDate, GfcDate anotherDate) {
      return elapsedDays - anotherDate.getElapsedDays();
    }

    @Override
    public int getElapsedDays(GfcDate gfcDate) {
      return this.elapsedDays;
    }

    @Override
    public void setDay(GfcDate gfcDate, int day) {
      gfcDate.setGfcState(new GfcDateMDYInitialized(0, day, 0));
    }

    @Override
    public int getDay(GfcDate gfcDate) {
      GfcDateState state = gfcDate.calcMDYAsState(elapsedDays);
      gfcDate.setGfcState(state);
      return state.getDay(gfcDate);
    }

    @Override
    public void setMonth(GfcDate gfcDate, int month) {
      gfcDate.setGfcState(new GfcDateMDYInitialized(month, 0, 0));
    }

    @Override
    public int getMonth(GfcDate gfcDate) {
      GfcDateState state = gfcDate.calcMDYAsState(elapsedDays);
      gfcDate.setGfcState(state);
      return state.getMonth(gfcDate);
    }

    @Override
    public void setYear(GfcDate gfcDate, int year) {
      gfcDate.setGfcState(new GfcDateMDYInitialized(0, 0, year));
    }

    @Override
    public int getYear(GfcDate gfcDate) {
      GfcDateState state = gfcDate.calcMDYAsState(elapsedDays);
      gfcDate.setGfcState(state);
      return state.getYear(gfcDate);
    }

    @Override
    public boolean isValid(GfcDate gfcDate) {
      return this.valid;
    }
  }

  /**
   * Date created with Elapsed days and the String, month, day and year must be calculated when
   * requested.
   */
  static class GfcDateMDYInitialized implements GfcDateState {

    private int month;
    private int day;
    private int year;
    private String strDate;

    public GfcDateMDYInitialized(int month, int day, int year) {
      this.month = month;
      this.day = day;
      this.year = year;
    }

    @Override
    public String toString(GfcDate gfcDate) {
      if (this.strDate == null) {
        this.strDate = String.format("%04d%02d%02d", this.year, this.month, this.day);
      }
      return this.strDate;
    }

    @Override
    public int hashCode(GfcDate gfcDate) {
      GfcDateState state = gfcDate.calcElapsedAsState(month, day, year);
      gfcDate.setGfcState(state);
      return state.hashCode(gfcDate);
    }

    @Override
    public int compareTo(GfcDate gfcDate, GfcDate anotherDate) {
      GfcDateState state = gfcDate.calcElapsedAsState(month, day, year);
      gfcDate.setGfcState(state);
      return gfcDate.compareTo(anotherDate);
    }

    @Override
    public int getElapsedDays(GfcDate gfcDate) {
      GfcDateState state = gfcDate.calcElapsedAsState(month, day, year);
      gfcDate.setGfcState(state);
      return gfcDate.getElapsedDays();
    }

    @Override
    public void setDay(GfcDate gfcDate, int day) {
      this.day = day;
    }

    @Override
    public int getDay(GfcDate gfcDate) {
      return this.day;
    }

    @Override
    public void setMonth(GfcDate gfcDate, int month) {
      this.month = month;
    }

    @Override
    public int getMonth(GfcDate gfcDate) {
      return this.month;
    }

    @Override
    public void setYear(GfcDate gfcDate, int year) {
      this.year = year;
    }

    @Override
    public int getYear(GfcDate gfcDate) {
      return this.year;
    }

    /**
     * validates based on the current month, day, year
     */
    @Override
    public boolean isValid(GfcDate gfcDate) {
      int[] days;

      if (month <= 0 || month > 12 || year <= 0 || day <= 0) {
        return false;
      }

      // make sure the day does not exceed the max for month
      days = gfcDate.isLeapYear(year) ? JULIAN_DAY_LEAP_YEAR : JULIAN_DAY;
      return day <= days[month] - days[month - 1];
    }
  }

}
