package com.mmm.his.cer.foundation.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Collection;

/**
 * @author Timothy Gallagher
 */
public class FlagsTest {

  public FlagsTest() {
  }

  @Test
  public void flagsTest() {
    DiagnosisCode dx = new DiagnosisCode();
    IFlags flags;
    GfcSex oldSex;
    GfcPoa poa;
    GfcEnum oldEnum;
    Collection<GfcEnum> enums;

    // check that the initial code has no flags
    assertEquals("Initial flags is not empty", true, dx.isFlagsEmpty());

    // get the list flags, which should not be null
    flags = dx.getFlags();
    assertEquals("Flags should not be a null object", true, flags != null);
    assertEquals("Get flags should still registers as empty", true, dx.isFlagsEmpty());

    flags.setFlag(GfcSex.FEMALE);
    assertEquals("Flag GfcSex.FEMALE value should be in list", true,
        flags.isFlagValueSet(GfcSex.FEMALE));
    assertEquals("Flag for any GfcSex value should be in list", true,
        flags.isFlagSet(GfcSex.class));

    oldSex = flags.setFlag(GfcSex.MALE);
    assertEquals("Old flag should not be null", true, oldSex != null);
    assertEquals("Old flag should be GfcSex.FEMALE value", true, oldSex == GfcSex.FEMALE);

    oldSex = flags.getFlag(GfcSex.class);
    assertEquals("Flag should not be null", true, oldSex != null);
    assertEquals("Flag should be GfcSex.MALE value", true, oldSex == GfcSex.MALE);
    assertEquals("Flag size should be 1", true, flags.size() == 1);

    oldEnum = flags.setFlag(GfcPoa.N);
    assertEquals("Flag should not be GfcSex.MALE value", true, oldEnum == null);
    assertEquals("Flag size should be 2", true, flags.size() == 2);

    poa = flags.getFlag(GfcPoa.class);
    assertEquals("Flag should not be null", true, poa != null);

    enums = flags.getFlags();
    assertEquals("Collection size should be 2", true, enums.size() == 2);

    enums = flags.getFlags(GfcPoa.class);
    assertEquals("Collection size should be 1 for PoaEnum", true, enums.size() == 1);

    enums = flags.getFlags(GfcEnum.class);
    assertEquals("Collection size should be 2 for all flags", true, enums.size() == 2);

    flags.clear();
    assertEquals("Ensure that clearing the flags is empty", true, dx.isFlagsEmpty());
  }
}
