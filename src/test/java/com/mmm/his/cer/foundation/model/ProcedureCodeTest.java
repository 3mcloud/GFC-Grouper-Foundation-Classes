/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmm.his.cer.foundation.model;

import com.mmm.his.cer.foundation.utility.GfcDate;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Timothy Gallagher
 */
public class ProcedureCodeTest {

  public ProcedureCodeTest() {
  }

  @Test
  public void testDefaultValues() {
    ProcedureCode code = new ProcedureCode();

    if (code.getValue() == null) {
      fail("Code - default value was incorrectly set to null");
    }

    checkUnknownDate(code.getDate());
  }


  @Test
  public void testConstructWithNullStringReset() {
    ProcedureCode code = new ProcedureCode(null);

    if (code.getValue() == null) {
      fail("Code - default value was incorrectly set to null");
    }

    checkUnknownDate(code.getDate());
  }

  private void checkUnknownDate(GfcDate date) {
    if (date == null) {
      fail("Code - default date was incorrectly set to null");
    } else if (date.isValid()) {
      fail("Code - default value was incorrectly set to Valid");
    }
  }

  @Test
  public void testConstructWithSpacesString() {
    String str = "    ";
    ProcedureCode code = new ProcedureCode(str);

    /*
     * since the code should take the string without change, we should get
     * the same one back
     */
    if (code.getValue() != str) {
      fail("Code - code set to string was incorrectly reset to " + code.getValue());
    }

    checkUnknownDate(code.getDate());
  }

  @Test
  public void testConstructWithStringDate() {
    String str = "    ";
    GfcDate date = GfcDate.now();
    ProcedureCode code = new ProcedureCode(str, date);

    /*
     * since the code should take the string without change, we should get
     * the same one back
     */
    if (code.getValue() != str) {
      fail("Code - code set to string was incorrectly reset to " + code.getValue());
    }

    if (date != date) {
      fail("Code - date set to date was incorrectly reset to " + date.toString());
    }
  }


}
