package com.mmm.his.cer.foundation.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.Test;

public class GfcPoaTest {

  @Test
  public void testFromValidChar() {
    GfcPoa poa = GfcPoa.fromChar('Y', GfcPoa.Y);
    assertThat(poa, is(GfcPoa.Y));
    assertThat(poa.charValue(), is('Y'));

    poa = GfcPoa.fromChar('y', GfcPoa.N);
    assertThat(poa, is(GfcPoa.Y));
    assertThat(poa.charValue(), is('Y'));

    poa = GfcPoa.fromChar(' ', GfcPoa.N);
    assertThat(poa, is(GfcPoa.BLANK));
    assertThat(poa.charValue(), is(' '));

  }

  @Test
  public void testFromInvalidChar() {
    GfcPoa poa = GfcPoa.fromChar('6');
    assertThat(poa, is(GfcPoa.E));
    assertThat(poa.charValue(), is('E'));

    poa = GfcPoa.fromChar((char) 0);
    assertThat(poa, is(GfcPoa.INVALID));
    assertThat(poa.charValue(), is((char) 0));
  }
}
