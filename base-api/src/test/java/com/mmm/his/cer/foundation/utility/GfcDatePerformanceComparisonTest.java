/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.mmm.his.cer.foundation.utility;

import com.google.common.base.Stopwatch;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Test and compares creation and usage performance compared to Joda date and Java Date
 *
 * @author Nick DiMucci
 */
@Ignore("This is not a test, just a demo displaying the timing comparison")
public class GfcDatePerformanceComparisonTest {

  @Test
  public void testAfter() throws Exception {

    System.out.println("====================== testAfter ======================");
    for (int t = 0; t < 19; ++t) {
      Stopwatch gfcStopwatch = Stopwatch.createStarted();
      for (int i = 0; i < 10000000; ++i) {
        GfcDate date1 = new GfcDate(2015, 10, 1);
        GfcDate date2 = new GfcDate(2016, 10, 1);

        if (date2.after(date1)) {
          // Doing stuff..
        }
      }
      System.out.println("GfcDate: "
          + gfcStopwatch.elapsed(TimeUnit.MILLISECONDS)
          + "\t\t\tms");
      gfcStopwatch.stop();
    }

    for (int t = 0; t < 19; ++t) {
      Stopwatch jodaStopwatch = Stopwatch.createStarted();
      for (int j = 0; j < 10000000; ++j) {
        DateTime date1 = new DateTime(2015, 10, 1, 0, 0);
        DateTime date2 = new DateTime(2016, 10, 1, 0, 0);

        if (date2.isAfter(date1)) {
          // Doing stuff..
        }
      }
      System.out.println("Joda Time: "
          + jodaStopwatch.elapsed(TimeUnit.MILLISECONDS)
          + "\t\t\tms");
      jodaStopwatch.stop();
    }
  }

  @Test
  public void testCompareTo() throws Exception {
    System.out.println("====================== testCompareTo ======================");
    for (int t = 0; t < 19; ++t) {
      Stopwatch gfcStopwatch = Stopwatch.createStarted();
      for (int i = 0; i < 10000000; ++i) {
        GfcDate date1 = new GfcDate(2015, 10, 1);
        GfcDate date2 = new GfcDate(2016, 10, 1);

        if (date2.compareTo(date1) > 0) {
          // Doing stuff..
        }
      }
      System.out.println("GfcDate: "
          + gfcStopwatch.elapsed(TimeUnit.MILLISECONDS)
          + "\t\t\tms");
      gfcStopwatch.stop();
    }

    for (int t = 0; t < 19; ++t) {
      Stopwatch jodaStopwatch = Stopwatch.createStarted();
      for (int j = 0; j < 10000000; ++j) {
        DateTime date1 = new DateTime(2015, 10, 1, 0, 0);
        DateTime date2 = new DateTime(2016, 10, 1, 0, 0);

        if (date2.compareTo(date1) > 0) {
          // Doing stuff..
        }
      }
      System.out.println("Joda Time: "
          + jodaStopwatch.elapsed(TimeUnit.MILLISECONDS)
          + "\t\t\tms");
      jodaStopwatch.stop();
    }
  }

  @Test
  public void testInterval() throws Exception {
    System.out.println("====================== testInterval ======================");
    for (int t = 0; t < 19; ++t) {
      Stopwatch gfcStopwatch = Stopwatch.createStarted();
      for (int i = 0; i < 10000000; ++i) {
        GfcDate date1 = new GfcDate(2015, 10, 1);
        GfcDate date2 = new GfcDate(2016, 10, 1);
        GfcDate date3 = new GfcDate(2016, 3, 1);

        // if (date3.getElapsedDays() > date1.getElapsedDays() && date3.getElapsedDays() <
        // date2.getElapsedDays()) {
        if (date3.containedWithin(date1, date2)) {
          // Doing stuff..
        }
      }
      System.out.println("GfcDate: "
          + gfcStopwatch.elapsed(TimeUnit.MILLISECONDS)
          + "\t\t\tms");
      gfcStopwatch.stop();
    }

    for (int t = 0; t < 19; ++t) {
      Stopwatch jodaStopwatch = Stopwatch.createStarted();
      for (int j = 0; j < 10000000; ++j) {
        DateTime date1 = new DateTime(2015, 10, 1, 0, 0);
        DateTime date2 = new DateTime(2016, 10, 1, 0, 0);
        DateTime date3 = new DateTime(2016, 3, 1, 0, 0);
        Interval interval = new Interval(date1, date2);

        if (interval.contains(date3)) {
          // Doing stuff..
        }
      }
      System.out.println("Joda Time: "
          + jodaStopwatch.elapsed(TimeUnit.MILLISECONDS)
          + "\t\t\tms");
      jodaStopwatch.stop();
    }
  }


  @Test
  public void testAfterLocalDate() throws Exception {

    System.out.println("====================== testAfterLocalDate ======================");
    for (int t = 0; t < 19; ++t) {
      Stopwatch gfcStopwatch = Stopwatch.createStarted();
      for (int i = 0; i < 10000000; ++i) {
        GfcDate date1 = new GfcDate(2015, 10, 1);
        GfcDate date2 = new GfcDate(2016, 10, 1);

        if (date2.after(date1)) {
          // Doing stuff..
        }
      }
      System.out.println("GfcDate: "
          + gfcStopwatch.elapsed(TimeUnit.MILLISECONDS)
          + "\t\t\tms");
      gfcStopwatch.stop();
    }

    for (int t = 0; t < 19; ++t) {
      Stopwatch jodaStopwatch = Stopwatch.createStarted();
      for (int j = 0; j < 10000000; ++j) {
        LocalDate date1 = new LocalDate(2015, 10, 1);
        LocalDate date2 = new LocalDate(2016, 10, 1);

        if (date2.isAfter(date1)) {
          // Doing stuff..
        }
      }
      System.out.println("Joda Time: "
          + jodaStopwatch.elapsed(TimeUnit.MILLISECONDS)
          + "\t\t\tms");
      jodaStopwatch.stop();
    }
  }

  @Test
  public void testCompareToLocalDate() throws Exception {
    System.out.println("====================== testCompareToLocalDate ======================");
    for (int t = 0; t < 19; ++t) {
      Stopwatch gfcStopwatch = Stopwatch.createStarted();
      for (int i = 0; i < 10000000; ++i) {
        GfcDate date1 = new GfcDate(2015, 10, 1);
        GfcDate date2 = new GfcDate(2016, 10, 1);

        if (date2.compareTo(date1) > 0) {
          // Doing stuff..
        }
      }
      System.out.println("GfcDate: "
          + gfcStopwatch.elapsed(TimeUnit.MILLISECONDS)
          + "\t\t\tms");
      gfcStopwatch.stop();
    }

    for (int t = 0; t < 19; ++t) {
      Stopwatch jodaStopwatch = Stopwatch.createStarted();
      for (int j = 0; j < 10000000; ++j) {
        LocalDate date1 = new LocalDate(2015, 10, 1);
        LocalDate date2 = new LocalDate(2016, 10, 1);

        if (date2.compareTo(date1) > 0) {
          // Doing stuff..
        }
      }
      System.out.println("Joda Time: "
          + jodaStopwatch.elapsed(TimeUnit.MILLISECONDS)
          + "\t\t\tms");
      jodaStopwatch.stop();
    }
  }

  @Test
  public void testIntervalLocalDate() throws Exception {
    System.out.println("====================== testIntervalLocalDate ======================");
    for (int t = 0; t < 19; ++t) {
      Stopwatch gfcStopwatch = Stopwatch.createStarted();
      for (int i = 0; i < 10000000; ++i) {
        GfcDate date1 = new GfcDate(2015, 10, 1);
        GfcDate date2 = new GfcDate(2016, 10, 1);
        GfcDate date3 = new GfcDate(2016, 3, 1);

        // if (date3.getElapsedDays() > date1.getElapsedDays() && date3.getElapsedDays() <
        // date2.getElapsedDays()) {
        if (date3.containedWithin(date1, date2)) {
          // Doing stuff..
        }
      }
      System.out.println("GfcDate: "
          + gfcStopwatch.elapsed(TimeUnit.MILLISECONDS)
          + "\t\t\tms");
      gfcStopwatch.stop();
    }

    for (int t = 0; t < 19; ++t) {
      Stopwatch jodaStopwatch = Stopwatch.createStarted();
      for (int j = 0; j < 10000000; ++j) {
        LocalDate date1 = new LocalDate(2015, 10, 1);
        LocalDate date2 = new LocalDate(2016, 10, 1);
        LocalDate date3 = new LocalDate(2016, 3, 1);
        Interval interval =
            new Interval(date1.toDateTimeAtStartOfDay(), date2.toDateTimeAtStartOfDay());

        if (interval.contains(date3.toDateTimeAtStartOfDay())) {
          // Doing stuff..
        }
      }
      System.out.println("Joda Time: "
          + jodaStopwatch.elapsed(TimeUnit.MILLISECONDS)
          + "\t\t\tms");
      jodaStopwatch.stop();
    }
  }

  @Test
  public void testCompareElapsedDays() throws Exception {
    System.out.println("====================== testCompareElapsedDays ======================");
    for (int t = 0; t < 19; ++t) {
      Stopwatch gfcStopwatch = Stopwatch.createStarted();
      for (int i = 0; i < 10000000; ++i) {
        GfcDate date1 = new GfcDate(2015, 10, 1);

        if (0 < date1.getElapsedDays()) {
          // Doing stuff..
        }
      }
      System.out.println("GfcDate: "
          + gfcStopwatch.elapsed(TimeUnit.MILLISECONDS)
          + "\t\t\tms");
      gfcStopwatch.stop();
    }

    LocalDate baseDate = new LocalDate(1849, 10, 1);
    for (int t = 0; t < 19; ++t) {
      Stopwatch jodaStopwatch = Stopwatch.createStarted();
      for (int j = 0; j < 10000000; ++j) {
        LocalDate date1 = new LocalDate(2015, 10, 1);

        if (0 < Days.daysBetween(baseDate, date1).getDays()) {
          // Doing stuff..
        }
      }
      System.out.println("Joda Time: "
          + jodaStopwatch.elapsed(TimeUnit.MILLISECONDS)
          + "\t\t\tms");
      jodaStopwatch.stop();
    }
  }

}
