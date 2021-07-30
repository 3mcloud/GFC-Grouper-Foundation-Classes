/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.mmm.his.cer.foundation.utility;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

/**
 * @author Timothy Gallagher
 */
public class SimpleGfcDateFormatTest {

  public SimpleGfcDateFormatTest() {
  }

  @Test
  public void testYYYYMMDD() {
    String strDatePattern = "YYYYMMDD";
    String strDate = "20141231";
    GfcDate date;
    SimpleGfcDateFormat format;
    String output;

    format = new SimpleGfcDateFormat(strDatePattern);
    try {
      date = format.parse(strDate);

      assertTrue("Bad year of "
          + date.getYear(), date.getYear() == 2014);
      assertTrue("Bad month of "
          + date.getMonth(), date.getMonth() == 12);
      assertTrue("Bad day of "
          + date.getDay(), date.getDay() == 31);

      output = format.format(date);
      assertTrue("Bad formatting of "
          + output, strDate.equals(output));

    } catch (ParseException exc) {
      fail("Error parsing at position"
          + exc.getErrorOffset());
    }

  }

  @Test
  public void testMMDDYYYY() {
    String strDatePattern = "MMDDYYYY";
    String strDate = "12312014";
    GfcDate date;
    SimpleGfcDateFormat format;
    String output;

    format = new SimpleGfcDateFormat(strDatePattern);
    try {
      date = format.parse(strDate);

      assertTrue("Bad year of "
          + date.getYear(), date.getYear() == 2014);
      assertTrue("Bad month of "
          + date.getMonth(), date.getMonth() == 12);
      assertTrue("Bad day of "
          + date.getDay(), date.getDay() == 31);

      output = format.format(date);
      assertTrue("Bad formatting of "
          + output, strDate.equals(output));

    } catch (ParseException ex) {
      fail("Error parsing at position"
          + ex.getErrorOffset());
    }

  }

  @Test
  public void testDDMMYYYY() {
    String strDatePattern = "DDMMYYYY";
    String strDate = "31122014";
    GfcDate date;
    SimpleGfcDateFormat format;
    String output;

    format = new SimpleGfcDateFormat(strDatePattern);
    try {
      date = format.parse(strDate);

      assertTrue("Bad year of "
          + date.getYear(), date.getYear() == 2014);
      assertTrue("Bad month of "
          + date.getMonth(), date.getMonth() == 12);
      assertTrue("Bad day of "
          + date.getDay(), date.getDay() == 31);

      output = format.format(date);
      assertTrue("Bad formatting of "
          + output, strDate.equals(output));

    } catch (ParseException ex) {
      fail("Error parsing at position"
          + ex.getErrorOffset());
    }

  }

  @Test
  public void testMMM_dd_yyyy() {
    String strDatePattern = "MMM dd, yyyy";
    String strDate = "Dec 31, 2014";
    GfcDate date;
    SimpleGfcDateFormat format;
    String output;

    format = new SimpleGfcDateFormat(strDatePattern);
    try {
      date = format.parse(strDate);

      assertTrue("Bad year of "
          + date.getYear(), date.getYear() == 2014);
      assertTrue("Bad month of "
          + date.getMonth(), date.getMonth() == 12);
      assertTrue("Bad day of "
          + date.getDay(), date.getDay() == 31);

      output = format.format(date);
      assertTrue("Bad formatting of "
          + output, strDate.equals(output));

    } catch (ParseException ex) {
      fail("Error parsing at position"
          + ex.getErrorOffset());
    }

  }

  @SuppressWarnings("unused")
  @Test
  public void testMMM_dd_yyyy_HH_mm_ss() {
    String strDatePattern = "MMM dd, yyyy HH:mm:ss";
    String strDate = "Dec 31, 2014 00:00:00";
    GfcDate date;
    Date jdate;
    SimpleGfcDateFormat format;
    String output;

    format = new SimpleGfcDateFormat(strDatePattern);
    try {
      date = format.parse(strDate);

      assertTrue("Bad year of "
          + date.getYear(), date.getYear() == 2014);
      assertTrue("Bad month of "
          + date.getMonth(), date.getMonth() == 12);
      assertTrue("Bad day of "
          + date.getDay(), date.getDay() == 31);

      jdate = date.getDate();
      assertTrue("GfcDate ("
          + date.getTime()
          + ") and java Date  ("
          + jdate.getTime()
          + ") millis don't match. ", date.getTime() == jdate.getTime());

      output = format.format(date);
      // assertTrue("Bad formatting of " + output, strDate.equals(output));

    } catch (ParseException ex) {
      fail("Error parsing at position"
          + ex.getErrorOffset());
    }

  }

}
