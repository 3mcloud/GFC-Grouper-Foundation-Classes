package com.mmm.his.cer.foundation.model;

import com.mmm.his.cer.foundation.transfer.DemoClaim;
import com.mmm.his.cer.foundation.utility.GfcDate;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;

public class TestSerialization {

  private static final File file = new File("testSerialization.txt");

  private static ObjectOutputStream oout;

  @BeforeClass
  public static void beforeClass() throws Exception {
    FileOutputStream fout = new FileOutputStream(file);
    oout = new ObjectOutputStream(fout);
  }

  @AfterClass
  public static void afterClass() throws Exception {
    oout.close();
    // Clean up
    Files.deleteIfExists(file.toPath());
  }

  @Test
  public void testGfcDate() throws Exception {
    serializeObject(new GfcDate(2015, 10, 1));
  }

  @Test
  public void testDiagnosisCode() throws Exception {
    serializeObject(new DiagnosisCode("A001"));
  }

  @Test
  public void testFlags() throws Exception {
    serializeObject(new Flags());
  }

  @Test
  public void testHcpcsCode() throws Exception {
    serializeObject(new HcpcsCode("AValue"));
  }

  @Test
  public void testProcedureCode() throws Exception {
    serializeObject(new ProcedureCode("000101"));
  }

  @Test
  public void testClaim() throws Exception {
    serializeObject(new DemoClaim());
  }

  @Test
  public void testGfcPoa() throws Exception {
    serializeObject(GfcPoa.Y);
  }

  @Test
  public void testGfcSex() throws Exception {
    serializeObject(GfcSex.MALE);
  }

  @Test
  public void testAgeCriteria() throws Exception {
    serializeObject(new AgeCriteria(1, 2, 3));
  }

  @Test
  public void testDatePeriod() throws Exception {
    serializeObject(new DatePeriod(new GfcDate(2015, 10, 1), new GfcDate(2016, 10, 1)));
  }

  private void serializeObject(Object object) {
    try {
      oout.writeObject(object);
    } catch (Exception exc) {
      exc.printStackTrace();
      Assert.fail(exc.getMessage());
    }
  }
}
