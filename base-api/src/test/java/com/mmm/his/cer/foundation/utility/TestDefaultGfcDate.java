/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.mmm.his.cer.foundation.utility;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * These are performance tests.
 *
 * @author Tim Gallagher - 3M HIS C&amp;ER
 */
public class TestDefaultGfcDate {

  @Test
  public void testGfcDateCreation() {
    @SuppressWarnings("unused")
    GfcDate date;
    long startTime = System.currentTimeMillis();
    long endTime;
    int idx = 0;

    while (idx++ < 1000000) {
      date = new GfcDate(2001, 1, 1);
    }
    endTime = System.currentTimeMillis();
    System.out.println("testGfcDateCreation: elapsed time for "
        + idx
        + " GfcDate creations: "
        + (endTime - startTime));
  }

  @SuppressWarnings("deprecation")
  @Test
  public void testJavaDate_Creation() {
    @SuppressWarnings("unused")
    Date date;
    long startTime = System.currentTimeMillis();
    long endTime;
    int idx = 0;

    while (idx++ < 1000000) {
      date = new Date(2001, 1, 1);
    }
    endTime = System.currentTimeMillis();
    System.out.println("testJavaDateCreation: elapsed time for "
        + idx
        + " Date creations: "
        + (endTime - startTime));
  }

  @Test
  public void testGfcDate_CreationGetMonthDay() {
    GfcDate date;
    long startTime = System.currentTimeMillis();
    long endTime;
    int idx = 0;
    @SuppressWarnings("unused")
    int month;
    @SuppressWarnings("unused")
    int day;

    while (idx++ < 1000000) {
      date = new GfcDate(2001, 1, 1);
      month = date.getMonth();
      day = date.getDay();
    }
    endTime = System.currentTimeMillis();
    System.out.println("testGfcDateCreationGetMonthDay: elapsed time for "
        + idx
        + " GfcDate creations: "
        + (endTime - startTime));
  }

  @SuppressWarnings("deprecation")
  @Test
  public void testJavaDate_CreationGetMonthDay() {
    Date date;
    long startTime = System.currentTimeMillis();
    long endTime;
    int idx = 0;
    @SuppressWarnings("unused")
    int month;
    @SuppressWarnings("unused")
    int day;

    while (idx++ < 1000000) {
      date = new Date(startTime);
      month = date.getMonth();
      day = date.getDate();
    }
    endTime = System.currentTimeMillis();
    System.out.println("testJavaDateCreationGetMonthDay: elapsed time for "
        + idx
        + " Date creations: "
        + (endTime - startTime));
  }

  @Test
  public void testGfcDate_CreationGetTime() {
    GfcDate date;
    long startTime = System.currentTimeMillis();
    long endTime;
    int idx = 0;
    @SuppressWarnings("unused")
    long dateTime;

    while (idx++ < 1000000) {
      date = new GfcDate(2001, 1, 1);
      dateTime = date.getTime();
    }

    endTime = System.currentTimeMillis();
    System.out.println("testGfcDateCreationGetTime: elapsed time for "
        + idx
        + " date creations: "
        + (endTime - startTime));
  }

  @SuppressWarnings("deprecation")
  @Test
  public void testJavaDate_CreationGetTime() {
    Date date;
    long startTime = System.currentTimeMillis();
    long endTime;
    int idx = 0;
    @SuppressWarnings("unused")
    long dateTime;

    while (idx++ < 1000000) {
      date = new Date(2001, 1, 1);
      dateTime = date.getTime();
    }

    endTime = System.currentTimeMillis();
    System.out.println("testJavaDateCreationGetTime: elapsed time for "
        + idx
        + " date creations: "
        + (endTime - startTime));
  }

  @SuppressWarnings("deprecation")
  @Test
  public void testGfcDate_GfcEquivelantToDateGetTime() {
    GfcDate date;
    Date jdate;
    long startTime = System.currentTimeMillis();
    long endTime;
    int idx = 0;
    long dateTime;

    jdate = new Date(1969 - 1900, 11, 31);
    while (idx++ < 1000000) {
      date = new GfcDate(1969, 12, 31);
      dateTime = date.getTime();

      jdate = new Date(dateTime);
      if (date.getMonth() != jdate.getMonth() + 1 || date.getYear() != jdate.getYear() + 1900
          || date.getDay() != jdate.getDate()) {
        Assert.fail(
            "Creating a java.util.Date from a DefaultGfcDate.getTime() does not produce the same month, day, year.");
      }
    }

    endTime = System.currentTimeMillis();
    System.out.println("testGfcDateGfcEquivelantToDateGetTime: elapsed time for "
        + idx
        + " date creations: "
        + (endTime - startTime));
  }

  @Test
  public void testGfcDate_GfcEquivelantToDateEqual() {
    GfcDate date;
    Date jdate;
    long startTime = System.currentTimeMillis();
    long endTime;
    int idx = 0;
    long dateTime;

    while (idx++ < 1000000) {
      date = new GfcDate(2001, 1, 1);
      dateTime = date.getTime() + 10000;

      jdate = new Date(dateTime);
      if (!date.equals(jdate)) {
        Assert.fail(
            "Creating a java.util.Date from a DefaultGfcDate.getTime() does not produce the same month, day, year.");
      }
    }

    endTime = System.currentTimeMillis();
    System.out.println("testGfcDateGfcEquivelantToDateEqual: elapsed time for "
        + idx
        + " date creations: "
        + (endTime - startTime));
  }

  @Test
  public void testGfcDate_AddDays() {
    GfcDate date;
    GfcDate date2;
    long startTime = System.currentTimeMillis();
    long endTime;
    int idx = 0;
    String expStr;
    int addDays;

    while (idx++ < 1000000) {
      date = new GfcDate(1849, 1, 1);

      expStr = "18500101";
      addDays = 365;
      date2 = new GfcDate(date.getElapsedDays() + addDays);
      if (!expStr.equals(date2.toString())) {
        Assert.fail("Creating a GfcDate from a ElapsedDays: starting date: "
            + date.toString()
            + " plus "
            + addDays
            + " did not results in "
            + expStr
            + " but instead = "
            + date2.toString());
      }

      expStr = "18491231";
      addDays = 364;
      date2 = new GfcDate(date.getElapsedDays() + addDays);
      if (!expStr.equals(date2.toString())) {
        Assert.fail("Creating a GfcDate from a ElapsedDays: starting date: "
            + date.toString()
            + " plus "
            + addDays
            + " did not results in "
            + expStr
            + " but instead = "
            + date2.toString());
      }

      date = new GfcDate(1900, 1, 1);
      expStr = "19001231";
      addDays = 364;
      date2 = new GfcDate(date.getElapsedDays() + addDays);
      if (!expStr.equals(date2.toString())) {
        Assert.fail("Creating a GfcDate from a ElapsedDays: starting date: "
            + date.toString()
            + " plus "
            + addDays
            + " did not result in "
            + expStr
            + " but instead = "
            + date2.toString());
      }

      date = new GfcDate(2000, 1, 1);
      expStr = "20001230";
      addDays = 364;
      date2 = new GfcDate(date.getElapsedDays() + addDays);
      if (!expStr.equals(date2.toString())) {
        Assert.fail("Creating a GfcDate from a ElapsedDays: starting date: "
            + date.toString()
            + " plus "
            + addDays
            + " did not results in "
            + expStr
            + " but instead = "
            + date2.toString());
      }
    }

    endTime = System.currentTimeMillis();
    System.out.println("testGfcDate_AddDays: elapsed time for "
        + idx
        + " GfcDate creations: "
        + (endTime - startTime));
  }

  @Test
  public void testGfcDate_BadDate() {
    GfcDate date;
    String strDate = "20121233";

    date = new GfcDate(2012, 12, 33);
    if (!strDate.equals(date.toString())) {
      Assert.fail("String of GfcDate does not match known string of date");
    }
  }

  @SuppressWarnings("deprecation")
  @Test
  public void testGfcDate_AddOneDay() {
    GfcDate date;
    GfcDate oneDayLater;

    date = new GfcDate(2012, 12, 31);
    oneDayLater = date.addDays(1);
    if (date.toString().equals(oneDayLater.toString())) {
      Assert.fail(
          "End of Year roll over - String of GfcDate is the same as the String GfcDate.addDary(1) but should be one day later");
    }

    date = new GfcDate(2015, 4, 30);
    oneDayLater = date.addDays(1);
    if (date.toString().equals(oneDayLater.toString())) {
      Assert.fail(
          "Mid Year roll over - String of GfcDate is the same as the String GfcDate.addDary(1) but should be one day later");
    }

    date = new GfcDate(60749);
    oneDayLater = new GfcDate(60750); // similar date.addDays(1);
    if (date.toString().equals(oneDayLater.toString())) {
      Assert.fail(
          "Mid Year roll over starting Today - String of GfcDate is the same as the String GfcDate.addDary(1) but should be one day later");
    }

    date = new GfcDate(new Date(1969 - 1900, 0, 1));
    oneDayLater = date.addDays(1);
    if (date.toString().equals(oneDayLater.toString())) {
      Assert.fail(
          "Mid Year roll over starting Today - String of GfcDate is the same as the String GfcDate.addDary(1) but should be one day later");
    }

    date = new GfcDate(new Date(1970 - 1900, 0, 1));
    oneDayLater = date.addDays(1);
    if (date.toString().equals(oneDayLater.toString())) {
      Assert.fail(
          "Mid Year roll over starting Today - String of GfcDate is the same as the String GfcDate.addDary(1) but should be one day later");
    }

    date = new GfcDate();
    oneDayLater = date.addDays(1);
    if (date.toString().equals(oneDayLater.toString())) {
      Assert.fail(
          "Today plus 1 - String of GfcDate is the same as the String GfcDate.addDary(1) but should be one day later");
    }

  }

  @Test
  public void test20121231() {
    GfcDate gd20121231 = new GfcDate("20121231");
    int elapsedValue = gd20121231.getElapsedDays();
    GfcDate gd20121231_bad = new GfcDate(elapsedValue);

    // object created with correct elapsed days...
    Assert.assertEquals(true, gd20121231.getElapsedDays() == gd20121231_bad.getElapsedDays());
    // but year month day fail
    boolean yearEqual = gd20121231_bad.getYear() == gd20121231.getYear();
    boolean monthEqual = gd20121231_bad.getMonth() == gd20121231.getMonth();
    boolean dayEqual = gd20121231_bad.getDay() == gd20121231.getDay();
    Assert.assertEquals("Year/Month/Day not matching. Orig="
        + Integer.toString(gd20121231.getYear())
        + Integer.toString(gd20121231.getMonth())
        + Integer.toString(gd20121231.getDay())
        + " and Derived="
        + Integer.toString(gd20121231_bad.getYear())
        + Integer.toString(gd20121231_bad.getMonth())
        + Integer.toString(gd20121231_bad.getDay()), true, yearEqual && monthEqual && dayEqual);
  }

  @Test
  public void testElapsedToMDY() {

    for (int i = 1, year = 1849; i < 50; i++, year++) {
      // set the date to be Dec 31 of the year
      GfcDate dateFromMDY = new GfcDate(year, 12, 31);
      GfcDate dateFromElapsed;
      dateFromElapsed = new GfcDate(dateFromMDY.getElapsedDays());
      if (dateFromMDY.getElapsedDays() != dateFromElapsed.getElapsedDays()) {
        System.out.println("Elapsed days: Date from MDY ("
            + year
            + ",12,31) of "
            + dateFromMDY.getElapsedDays()
            + " does not match "
            + dateFromElapsed.getElapsedDays());
      }

      boolean yearEqual = dateFromElapsed.getYear() == dateFromMDY.getYear();
      boolean monthEqual = dateFromElapsed.getMonth() == dateFromMDY.getMonth();
      boolean dayEqual = dateFromElapsed.getDay() == dateFromMDY.getDay();
      Assert.assertEquals("Year/Month/Day not matching. Orig="
          + Integer.toString(dateFromMDY.getYear())
          + Integer.toString(dateFromMDY.getMonth())
          + Integer.toString(dateFromMDY.getDay())
          + " and Derived="
          + Integer.toString(dateFromElapsed.getYear())
          + Integer.toString(dateFromElapsed.getMonth())
          + Integer.toString(dateFromElapsed.getDay()), true, yearEqual && monthEqual && dayEqual);
    }
  }

  @Test
  public void testLeapYearsWithStringDates() {

    for (int i = 1, year = 1849; i < 50; i++, year++) {
      // set the date to be Dec 31 of the year
      GfcDate dateFromMDY = new GfcDate(Integer.toString(year)
          + "1231");
      GfcDate dateFromElapsed;
      dateFromElapsed = new GfcDate(dateFromMDY.getElapsedDays());
      if (dateFromMDY.getElapsedDays() != dateFromElapsed.getElapsedDays()) {
        System.out.println("Elapsed days (From String date): Date from MDY ("
            + year
            + ",12,31) of "
            + dateFromMDY.getElapsedDays()
            + " does not match "
            + dateFromElapsed.getElapsedDays());
      }
    }
  }

  @Test
  public void test20150229() {
    String strDate = "20150229";
    GfcDate gd20150229_bad = new GfcDate(strDate);

    Assert.assertEquals(false, gd20150229_bad.isValid());
    Assert.assertEquals(true, strDate.equals(gd20150229_bad.toString()));

    int elapsedValue = gd20150229_bad.getElapsedDays();
    GfcDate gd20150229_elapsed = new GfcDate(elapsedValue);

    // object created with correct elapsed days...
    Assert.assertEquals(true,
        gd20150229_elapsed.getElapsedDays() == gd20150229_bad.getElapsedDays());
    // but year month day fail with bad non-leap year values assigned
    boolean yearEqual = gd20150229_bad.getYear() == gd20150229_elapsed.getYear();
    boolean monthEqual = gd20150229_bad.getMonth() == gd20150229_elapsed.getMonth();
    boolean dayEqual = gd20150229_bad.getDay() == gd20150229_elapsed.getDay();
    Assert.assertEquals(false, yearEqual && monthEqual && dayEqual);
  }

  /**
   * This tests that the bad date, as String, is identified as bad but allows the String date to
   * pass through
   */
  @Test
  public void test20150140() {
    String strDate = "20150140";
    GfcDate gd20150140_bad = new GfcDate(strDate);

    Assert.assertEquals(false, gd20150140_bad.isValid());
    Assert.assertEquals(true, strDate.equals(gd20150140_bad.toString()));

    int elapsedValue = gd20150140_bad.getElapsedDays();
    GfcDate gd20150140_elapsed = new GfcDate(elapsedValue);

    // object created with correct elapsed days...
    Assert.assertEquals(true,
        gd20150140_elapsed.getElapsedDays() == gd20150140_bad.getElapsedDays());
    // but year month day fail with bad non-leap year values assigned
    boolean yearEqual = gd20150140_bad.getYear() == gd20150140_elapsed.getYear();
    boolean monthEqual = gd20150140_bad.getMonth() == gd20150140_elapsed.getMonth();
    boolean dayEqual = gd20150140_bad.getDay() == gd20150140_elapsed.getDay();
    Assert.assertEquals(false, gd20150140_bad.isValid() && yearEqual && monthEqual && dayEqual);
  }

  @Test
  public void test20150101() {
    GfcDate gd20150101 = new GfcDate("20150101");
    int elapsedValue = gd20150101.getElapsedDays();
    GfcDate gd20150101_bad = new GfcDate(elapsedValue);

    // object created with correct elapsed days...
    Assert.assertEquals(true, gd20150101.getElapsedDays() == gd20150101_bad.getElapsedDays());
    Assert.assertEquals(true, gd20150101.isValid());
    Assert.assertEquals(true, gd20150101_bad.isValid());

    // but year month fail with year 2014 and month 13 values
    boolean yearEqual = gd20150101_bad.getYear() == gd20150101.getYear();
    boolean monthEqual = gd20150101_bad.getMonth() == gd20150101.getMonth();
    boolean dayEqual = gd20150101_bad.getDay() == gd20150101.getDay();
    Assert.assertEquals(true, yearEqual && monthEqual && dayEqual);
  }

  @Test
  public void test20160101() {
    GfcDate gd20160101 = new GfcDate("20160101");
    int elapsedValue = gd20160101.getElapsedDays();
    GfcDate gd20160101_bad = new GfcDate(elapsedValue);

    // object created with correct elapsed days...
    Assert.assertEquals(true, gd20160101.getElapsedDays() == gd20160101_bad.getElapsedDays());
    Assert.assertEquals(true, gd20160101.isValid());
    Assert.assertEquals(true, gd20160101_bad.isValid());

    // but year month fail with year 2015 and month 13 values
    boolean yearEqual = gd20160101_bad.getYear() == gd20160101.getYear();
    boolean monthEqual = gd20160101_bad.getMonth() == gd20160101.getMonth();
    boolean dayEqual = gd20160101_bad.getDay() == gd20160101.getDay();
    Assert.assertEquals(true, yearEqual && monthEqual && dayEqual);
  }


  @Test
  public void testBadDay() throws Exception {
    Assert.assertEquals(false, new GfcDate(2015, 12, 32).isValid());
    Assert.assertEquals(false, new GfcDate("20151232").isValid());
    Assert.assertEquals(false, new GfcDate(2100, 2, 29).isValid());
    Assert.assertEquals(false, new GfcDate("21000229").isValid());

  }

}
