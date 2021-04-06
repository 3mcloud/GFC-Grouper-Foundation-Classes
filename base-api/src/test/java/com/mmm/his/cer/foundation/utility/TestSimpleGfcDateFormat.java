/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmm.his.cer.foundation.utility;

import org.junit.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 *
 * @author Tim Gallagher
 */
public class TestSimpleGfcDateFormat {

  public TestSimpleGfcDateFormat() {
  }

  @BeforeClass
  public static void setUpClass() {
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
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

      assertTrue("Bad year of " + date.getYear(), date.getYear() == 2014);
      assertTrue("Bad month of " + date.getMonth(), date.getMonth() == 12);
      assertTrue("Bad day of " + date.getDay(), date.getDay() == 31);

      output = format.format(date);
      assertTrue("Bad formatting of " + output, strDate.equals(output));

    } catch (ParseException exc) {
      fail("Error parsing at position" + exc.getErrorOffset());
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

      assertTrue("Bad year of " + date.getYear(), date.getYear() == 2014);
      assertTrue("Bad month of " + date.getMonth(), date.getMonth() == 12);
      assertTrue("Bad day of " + date.getDay(), date.getDay() == 31);

      output = format.format(date);
      assertTrue("Bad formatting of " + output, strDate.equals(output));

    } catch (ParseException exc) {
      fail("Error parsing at position" + exc.getErrorOffset());
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

      assertTrue("Bad year of " + date.getYear(), date.getYear() == 2014);
      assertTrue("Bad month of " + date.getMonth(), date.getMonth() == 12);
      assertTrue("Bad day of " + date.getDay(), date.getDay() == 31);

      output = format.format(date);
      assertTrue("Bad formatting of " + output, strDate.equals(output));

    } catch (ParseException exc) {
      fail("Error parsing at position" + exc.getErrorOffset());
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

      assertTrue("Bad year of " + date.getYear(), date.getYear() == 2014);
      assertTrue("Bad month of " + date.getMonth(), date.getMonth() == 12);
      assertTrue("Bad day of " + date.getDay(), date.getDay() == 31);

      output = format.format(date);
      assertTrue("Bad formatting of " + output, strDate.equals(output));

    } catch (ParseException exc) {
      fail("Error parsing at position" + exc.getErrorOffset());
    }

  }

  @Test
  public void testMMM_dd_yyyy_HH_mm_ss() {
    String strDatePattern = "MMM dd, yyyy HH:mm:ss";
    String strDate = "Dec 31, 2014 00:00:00";
    GfcDate date;
    Date jdate;
    SimpleGfcDateFormat format;
    @SuppressWarnings("unused")
    String output;

    format = new SimpleGfcDateFormat(strDatePattern);
    try {
      date = format.parse(strDate);

      assertTrue("Bad year of " + date.getYear(), date.getYear() == 2014);
      assertTrue("Bad month of " + date.getMonth(), date.getMonth() == 12);
      assertTrue("Bad day of " + date.getDay(), date.getDay() == 31);

      jdate = date.getDate();
      assertTrue("GfcDate (" + date.getTime() + ") and java Date  (" + jdate.getTime() + ") millis don't match. ",
        date.getTime() == jdate.getTime());

      output = format.format(date);
//            assertTrue("Bad formatting of " + output, strDate.equals(output));

    } catch (ParseException exc) {
      fail("Error parsing at position" + exc.getErrorOffset());
    }

  }

  public void testGfcDateFromString() {
    // standard GFC format
    String strDate = "19480411";
    GfcDate date = new GfcDate(strDate);

    validateDateAfterOps(date, 1948, 4, 11, strDate);
  }

  @Test
  public void testCustomConversion_mmddyyyy() throws ParseException {
    String strDate = "19480411";
    String strDate_mmddyyyy = "04111948";

    SimpleGfcDateFormat sdf = new SimpleGfcDateFormat("mmddyyyy");
    GfcDate date = sdf.parse(strDate_mmddyyyy);
    validateDateAfterOps(date, 1948, 4, 11, strDate);
  }

  @Test
  public void testCustomConversion_mmddyyyy_w_slash() throws ParseException {
    String strDate = "19480411";
    String strDate_mmddyyyy = "04/11/1948";

    SimpleGfcDateFormat sdf = new SimpleGfcDateFormat("mm/dd/yyyy");
    GfcDate date = sdf.parse(strDate_mmddyyyy);
    validateDateAfterOps(date, 1948, 4, 11, strDate);

  }
  
  @Test
  public void testCustomConversion_ddmmyyyy_w_slash() throws ParseException {
    String strDate = "19480411";
    String strDate_mmddyyyy = "11/04/1948";

    SimpleGfcDateFormat sdf = new SimpleGfcDateFormat("dd/mm/yyyy");
    GfcDate date = sdf.parse(strDate_mmddyyyy);
    validateDateAfterOps(date, 1948, 4, 11, strDate);

  }
  
  @Test
  public void testGfcDateCreatedWithDateDirectly() {
    String strDate = "19480411";
    GfcDate date = new GfcDate(strDate);
    date = new GfcDate(date.getDate());

    validateDateAfterOps(date, 1948, 4, 11, strDate);
  }
  
//  /**
//   * @deprecated This will fail due to an issue with Date.getTime() and locales.  The GfcDate
//   * constructor has also been deprecated
//  */
//  @Test 
//  public void testGfcDateCreatedWithDateGetTime() {
//    String strDate = "19480411";
//    GfcDate date = new GfcDate(strDate);
//    date = new GfcDate(date.getDate().getTime());
//
//    validateDateAfterOps(date, 1948, 4, 11, strDate);
//  }

  /**
   * Tests that the date values are as expected and does not change after certain operations.
   * 
   * @param date non-null GfcDate
   * @param year expected year
   * @param month expected month number with Jan = 1
   * @param day expected day of month
   * @param strValue value expected as normal GfcDate.toString(), which is yyyymmdd format
   */
  public static void validateDateAfterOps(GfcDate date, int year, int month, int day, String strValue) {

    assertTrue("Bad year of " + date.getYear(), date.getYear() == year);
    assertTrue("Bad month of " + date.getMonth(), date.getMonth() == month);
    assertTrue("Bad day of " + date.getDay(), date.getDay() == day);
    assertTrue("Bad string " + date.toString(), strValue.equals(date.toString()));

    date.getDate();
    assertTrue("After getDate(). Bad year of " + date.getYear(), date.getYear() == year);
    assertTrue("After getDate(). Bad month of " + date.getMonth(), date.getMonth() == month);
    assertTrue("After getDate(). Bad day of " + date.getDay(), date.getDay() == day);
    assertTrue("After getDate(). Bad string " + date.toString(), strValue.equals(date.toString()));

    date.toString();
    assertTrue("After toString(). Bad year of " + date.getYear(), date.getYear() == year);
    assertTrue("After toString(). Bad month of " + date.getMonth(), date.getMonth() == month);
    assertTrue("After toString(). Bad day of " + date.getDay(), date.getDay() == day);
    assertTrue("After toString(). Bad string " + date.toString(), strValue.equals(date.toString()));

    date.getElapsedDays();
    assertTrue("After getElapsedDays(). Bad year of " + date.getYear(), date.getYear() == year);
    assertTrue("After getElapsedDays(). Bad month of " + date.getMonth(), date.getMonth() == month);
    assertTrue("After getElapsedDays(). Bad day of " + date.getDay(), date.getDay() == day);
    assertTrue("After getElapsedDays(). Bad string " + date.toString(), strValue.equals(date.toString()));
  }
  
  
    
    
//    
//    System.out.println("GfcDate 1 - parsed using SimpleGfcDateFormat mmddyyyy, output w/ GfcDate.toString(): " + gfcDate1.toString());
//
//    GfcDate gfcDate2 = new GfcDate(gfcDate1.getDate());
//    System.out.println("GfcDate 2 - from GfcDate1 w/ constructor getDate(): " + gfcDate2.toString());
//
//    Date tmpDate = gfcDate1.getDate();
//    GfcDate gfcDate3 = new GfcDate(tmpDate.getYear() + 1900, tmpDate.getMonth() + 1, tmpDate.getDate());
//    System.out.println("GfcDate 3 - from date.getYear()...: " + gfcDate3.toString());
//    //assertTrue(sdf.format(date).equals(gfcDate.toString()));
//
//    SimpleGfcDateFormat sdf2 = new SimpleGfcDateFormat();
//    GfcDate gfcDate4 = sdf2.parse("19700101");
//    System.out.println("GfcDate 4 millis: " + gfcDate4.getTime());
//    System.out.println("GfcDate 4: " + gfcDate4.toString());
//
  
}
