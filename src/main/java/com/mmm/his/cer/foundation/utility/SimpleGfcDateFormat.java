package com.mmm.his.cer.foundation.utility;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


interface HasPattern {
  String getPattern();
}

/**
 * Provides a way to convert a date string to a GfcDate when using a built in conversion, or to
 * a standard Date when not using a built in format.  GfcDate focus on month,day,year which reduces
 * the overhead of time and local calculations.
 * 
 * @author Tim Gallagher based on the original work by Mark Davis, Chen-Lieh Huang, Alan Liu
 */
public class SimpleGfcDateFormat {

  static final HasPattern[] FORMATS = new HasPattern[] {
    new YYYYMMDD_Format(), new DDMMYYY_Format(), new MMDDYYY_Format(),
    new DDMMYYYSlash_Format(), new MMDDYYYSlash_Format() };
  
  private Format simpleDateFormat;
  private boolean builtInFormat;
  private String pattern;

  /**
   * Constructs a <code>SimpleGfcDateFormat</code> using the default pattern and date format symbols
   * for the default locale. <b>Note:</b> This constructor may not support all locales. For full
   * coverage, use the factory methods in the {@link DateFormat} class.
   */
  public SimpleGfcDateFormat() {
    this("yyyyMMdd");
  }

  /**
   * Constructs a <code>SimpleGfcDateFormat</code> using the given pattern and the default date
   * format symbols for the default locale. <b>Note:</b> This constructor may not support all
   * locales. For full coverage, use the factory methods in the {@link DateFormat} class.
   *
   * @param pattern the pattern describing the date and time format
   * @exception NullPointerException if the given pattern is null
   * @exception IllegalArgumentException if the given pattern is invalid
   */
  public SimpleGfcDateFormat(String pattern) {
    this(pattern, Locale.getDefault());
  }

  /**
   * Constructs a <code>SimpleGfcDateFormat</code> using the given pattern and the default date
   * format symbols for the given locale. <b>Note:</b> This constructor may not support all locales.
   * For full coverage, use the factory methods in the {@link DateFormat} class.
   *
   * @param pattern the pattern describing the date and time format
   * @param locale the locale whose date format symbols should be used
   * @exception NullPointerException if the given pattern is null
   * @exception IllegalArgumentException if the given pattern is invalid
   */
  public SimpleGfcDateFormat(String pattern, Locale locale) {
    this.pattern = pattern;

    for (HasPattern format : FORMATS) {
      if (format.getPattern().equalsIgnoreCase(pattern)) {
        this.simpleDateFormat = (Format) format;
        this.builtInFormat = true;
        break;
      }
    }
    
    if (this.simpleDateFormat == null) {
      this.simpleDateFormat = new SimpleDateFormat(pattern, locale);
    }
    
  }

  /**
   * Constructs a <code>SimpleGfcDateFormat</code> using the given pattern and date format symbols.
   *
   * @param pattern the pattern describing the date and time format
   * @param formatSymbols the date format symbols to be used for formatting
   * @exception NullPointerException if the given pattern or formatSymbols is null
   * @exception IllegalArgumentException if the given pattern is invalid
   */
  public SimpleGfcDateFormat(String pattern, DateFormatSymbols formatSymbols) {
    this.pattern = pattern;

    if (YYYYMMDD_Format.PATTERN.equalsIgnoreCase(pattern)) {
      simpleDateFormat = new YYYYMMDD_Format();
      builtInFormat = true;
    } else if (DDMMYYY_Format.PATTERN.equalsIgnoreCase(pattern)) {
      simpleDateFormat = new DDMMYYY_Format();
      builtInFormat = true;
    } else if (MMDDYYY_Format.PATTERN.equalsIgnoreCase(pattern)) {
      simpleDateFormat = new MMDDYYY_Format();
      builtInFormat = true;
    } else {
      simpleDateFormat = new SimpleDateFormat(pattern, formatSymbols);
    }
  }

  /**
   * Gets the date formatter with the given formatting style for the given locale.
   *
   * @param dateStyle the given formatting style. For example, SHORT for "M/d/yy" in the US locale.
   * @param locale the given locale.
   */
  public SimpleGfcDateFormat(int dateStyle, Locale locale) {
    this.pattern = Integer.toString(dateStyle);

    simpleDateFormat = DateFormat.getDateInstance(dateStyle, locale);
  }

  /**
   * Formats a Date into a date/time string.
   *
   * @param date the time value to be formatted into a time string.
   * @return the formatted time string.
   */
  public String format(GfcDate date) {
    return builtInFormat ? simpleDateFormat.format(date) : simpleDateFormat.format(date.getDate());
  }

  /**
   * Parses the string to convert it to a GfcDate
   *
   * @param source to parse
   * @return date object
   * @throws ParseException for invalid source
   */
  public GfcDate parse(String source) throws ParseException {
    Object obj;
    GfcDate gfcDate;

    obj = simpleDateFormat.parseObject(source);
    if (obj instanceof Date) {
      final Date date = (Date) obj;
      gfcDate = new GfcDate(date);
    } else if (obj instanceof GfcDate) {
      gfcDate = (GfcDate) obj;
    } else {
      throw new ParseException("Can not handle converted class of type '"
          + obj.getClass().getName()
          + "'", 0);
    }
    return gfcDate;
  }

  /**
   * Gets the pattern or DateStyle used to construct this format
   *
   * @return non-null string for pattern or DateStyle
   */
  public String toPattern() {
    return this.pattern;
  }

  /**
   * Basic format for date as yyyyMMdd that avoids the overhead of the SimpleDateFormat
   */
  static class YYYYMMDD_Format extends Format implements HasPattern {
    private static final long serialVersionUID = 1L;
    public static final String PATTERN = "YYYYMMDD";

    @Override
    public String getPattern() {
      return PATTERN;
    }

    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
      GfcDate gfcDate = (GfcDate) obj;

      toAppendTo.append(gfcDate.getYear());
      if (gfcDate.getMonth() < 10) {
        toAppendTo.append("0");
      }
      toAppendTo.append(gfcDate.getMonth());
      if (gfcDate.getDay() < 10) {
        toAppendTo.append("0");
      }
      toAppendTo.append(gfcDate.getDay());

      return toAppendTo;
    }

    /**
     * Parses the date string starting at a specific location within the string.
     * 
     * @param source full string to parse
     * @param pos position to start with.
     * @return non-null GfcDate
     */
    @Override
    public Object parseObject(String source, ParsePosition pos) {
      final int year;
      final int month;
      final int day;
      int parsePos = pos.getIndex();

      year = Integer.parseInt(source.substring(parsePos, parsePos + 4));
      parsePos += 4;
      month = Integer.parseInt(source.substring(parsePos, parsePos + 2));
      parsePos += 2;
      day = Integer.parseInt(source.substring(parsePos, parsePos + 2));
      parsePos += 2;
      pos.setIndex(parsePos);

      return new GfcDate(year, month, day);
    }
  }

  /**
   * Basic format for date as MMddyyyy that avoids the overhead of the SimpleDateFormat
   */
  static class MMDDYYY_Format extends Format implements HasPattern {
    private static final long serialVersionUID = 1L;
    public static final String PATTERN = "MMDDYYYY";

    @Override
    public String getPattern() {
      return PATTERN;
    }

    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
      GfcDate gfcDate = (GfcDate) obj;

      if (gfcDate.getMonth() < 10) {
        toAppendTo.append("0");
      }
      toAppendTo.append(gfcDate.getMonth());
      if (gfcDate.getDay() < 10) {
        toAppendTo.append("0");
      }
      toAppendTo.append(gfcDate.getDay());
      toAppendTo.append(gfcDate.getYear());

      return toAppendTo;
    }

    /**
     * Parses the date string starting at a specific location within the string.
     * 
     * @param source full string to parse
     * @param pos position to start with.
     * @return non-null GfcDate
     */
    @Override
    public Object parseObject(String source, ParsePosition pos) {
      final int year;
      final int month;
      final int day;
      int parsePos = pos.getIndex();

      month = Integer.parseInt(source.substring(parsePos, parsePos + 2));
      parsePos += 2;
      day = Integer.parseInt(source.substring(parsePos, parsePos + 2));
      parsePos += 2;
      year = Integer.parseInt(source.substring(parsePos, parsePos + 4));
      parsePos += 4;
      pos.setIndex(parsePos);

      return new GfcDate(year, month, day);
    }
  }

  /**
   * Basic format for date as MMddyyyy that avoids the overhead of the SimpleDateFormat
   */
  static class DDMMYYY_Format extends Format implements HasPattern {
    private static final long serialVersionUID = 1L;
    public static final String PATTERN = "DDMMYYYY";

    @Override
    public String getPattern() {
      return PATTERN;
    }

    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
      GfcDate gfcDate = (GfcDate) obj;

      if (gfcDate.getDay() < 10) {
        toAppendTo.append("0");
      }
      toAppendTo.append(gfcDate.getDay());
      if (gfcDate.getMonth() < 10) {
        toAppendTo.append("0");
      }
      toAppendTo.append(gfcDate.getMonth());
      toAppendTo.append(gfcDate.getYear());

      return toAppendTo;
    }

    /**
     * Parses the date string starting at a specific location within the string.
     * 
     * @param source full string to parse
     * @param pos position to start with.
     * @return non-null GfcDate
     */
    @Override
    public Object parseObject(String source, ParsePosition pos) {
      final int year;
      final int month;
      final int day;
      int parsePos = pos.getIndex();

      day = Integer.parseInt(source.substring(parsePos, parsePos + 2));
      parsePos += 2;
      month = Integer.parseInt(source.substring(parsePos, parsePos + 2));
      parsePos += 2;
      year = Integer.parseInt(source.substring(parsePos, parsePos + 4));
      parsePos += 4;
      pos.setIndex(parsePos);

      return new GfcDate(year, month, day);
    }
  }

  /**
   * Basic format for date as MMddyyyy that avoids the overhead of the SimpleDateFormat
   */
  static class MMDDYYYSlash_Format extends Format implements HasPattern {
    private static final long serialVersionUID = 1L;
    public static final String PATTERN = "MM/DD/YYYY";

    @Override
    public String getPattern() {
      return PATTERN;
    }

    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
      GfcDate gfcDate = (GfcDate) obj;

      if (gfcDate.getMonth() < 10) {
        toAppendTo.append("0");
      }
      toAppendTo.append(gfcDate.getMonth());
      toAppendTo.append('/');

      if (gfcDate.getDay() < 10) {
        toAppendTo.append("0");
      }
      toAppendTo.append(gfcDate.getDay());
      toAppendTo.append('/');
      
      toAppendTo.append(gfcDate.getYear());

      return toAppendTo;
    }

    /**
     * Parses the date string starting at a specific location within the string.
     * This input can be either single char month or day.
     * 
     * @param source full string to parse
     * @param pos position to start with.
     * @return non-null GfcDate
     */
    @Override
    public Object parseObject(String source, ParsePosition pos) {
      final int year;
      final int month;
      final int day;
      int parsePos = pos.getIndex();
      int firstSlash = source.indexOf('/', parsePos);

      month = Integer.parseInt(source.substring(parsePos, firstSlash++));
      int secondSlash = source.indexOf('/', firstSlash);
      day = Integer.parseInt(source.substring(firstSlash, secondSlash++));
      year = Integer.parseInt(source.substring(secondSlash));
      pos.setIndex(parsePos + 10);

      return new GfcDate(year, month, day);
    }
  }

  /**
   * Basic format for date as MMddyyyy that avoids the overhead of the SimpleDateFormat
   */
  static class DDMMYYYSlash_Format extends Format implements HasPattern {
    private static final long serialVersionUID = 1L;
    public static final String PATTERN = "DD/MM/YYYY";

    @Override
    public String getPattern() {
      return PATTERN;
    }
    
    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
      GfcDate gfcDate = (GfcDate) obj;

      if (gfcDate.getDay() < 10) {
        toAppendTo.append("0");
      }
      toAppendTo.append(gfcDate.getDay());
      toAppendTo.append('/');
      
      if (gfcDate.getMonth() < 10) {
        toAppendTo.append("0");
      }
      toAppendTo.append(gfcDate.getMonth());
      toAppendTo.append('/');

      toAppendTo.append(gfcDate.getYear());

      return toAppendTo;
    }

    /**
     * Parses the date string starting at a specific location within the string.
     * This input can be either single char month or day.
     * 
     * @param source full string to parse
     * @param pos position to start with.
     * @return non-null GfcDate
     */
    @Override
    public Object parseObject(String source, ParsePosition pos) {
      final int year;
      final int month;
      final int day;
      int parsePos = pos.getIndex();
      int firstSlash = source.indexOf('/', parsePos);

      day = Integer.parseInt(source.substring(parsePos, firstSlash++));
      int secondSlash = source.indexOf('/', firstSlash);
      month = Integer.parseInt(source.substring(firstSlash, secondSlash++));
      year = Integer.parseInt(source.substring(secondSlash));
      pos.setIndex(parsePos + 10);

      return new GfcDate(year, month, day);
    }
  }

  
}
