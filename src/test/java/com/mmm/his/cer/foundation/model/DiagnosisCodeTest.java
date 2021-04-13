package com.mmm.his.cer.foundation.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

import org.hamcrest.core.IsNull;
import org.junit.Test;

public class DiagnosisCodeTest {

  public DiagnosisCodeTest() {}

  @Test
  public void testDefaultValues() {
    DiagnosisCode code = new DiagnosisCode();

    assertThat(code.getValue(), not(IsNull.nullValue()));
    assertThat(code.getPresentOnAdmissionEnum(), is(GfcPoa.E));
  }

  @Test
  public void testConstructWithNullStringReset() {
    DiagnosisCode code = new DiagnosisCode(null);

    assertThat(code.getValue(), is(""));
    assertThat(code.getPresentOnAdmissionEnum(), is(GfcPoa.E));
  }

  @Test
  public void testConstructWithSpacesString() {
    String str = "    ";
    DiagnosisCode code = new DiagnosisCode(str);

    assertThat(code.getValue(), is(str));
    assertThat(code.getPresentOnAdmissionEnum(), is(GfcPoa.E));
  }

  @Test
  public void testConstructWithCharPoa() {
    char poaChar = 'Y';
    GfcPoa poa = GfcPoa.fromChar('Y', GfcPoa.Y);
    DiagnosisCode code = new DiagnosisCode("A001", poa);

    assertThat(code.getPresentOnAdmissionEnum().charValue(), is('Y'));
    assertThat(code.getPresentOnAdmissionEnum(), is(poa));
  }

  @Test
  public void testConstructWithEnum() {
    DiagnosisCode code = new DiagnosisCode("A001", GfcPoa.Y);

    assertThat(code.getValue(), is("A001"));
    assertThat(code.getPresentOnAdmissionEnum(), is(GfcPoa.Y));
  }

  @Test
  public void testPoaSetterWithBadValue() {
    DiagnosisCode code = new DiagnosisCode();
    code.setPresentOnAdmission(GfcPoa.fromChar('9', GfcPoa.U));

    assertThat(code.getPresentOnAdmissionEnum(), is(GfcPoa.U));
  }
}
