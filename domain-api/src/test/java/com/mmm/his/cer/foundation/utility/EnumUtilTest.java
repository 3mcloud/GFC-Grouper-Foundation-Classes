package com.mmm.his.cer.foundation.utility;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import com.mmm.his.cer.foundation.exception.NotFoundException;
import com.mmm.his.cer.foundation.model.GfcEnum;
import org.junit.Test;

/**
 *
 *
 * @author Thomas Naeff
 *
 */
public class EnumUtilTest {

  enum TestGfcEnum implements GfcEnum {

    ELEMENT_1(
        'a',
        "First element"),
    ELEMENT_2(
        'b',
        "Second element");

    private final char charVal;
    private final String desc;

    private TestGfcEnum(char charVal, String desc) {
      this.charVal = charVal;
      this.desc = desc;
    }

    @Override
    public int intValue() {
      return ordinal();
    }

    @Override
    public char charValue() {
      return charVal;
    }

    @Override
    public String getDescription() {
      return desc;
    }


  }



  @Test
  public void testEnumUtil() throws Exception {

    // NotFoundException is an unchecked exception since GFC v3.4.3 (was replaced with
    // IllegalArgumentException at some point and reverted again in v3.4.8)
    TestGfcEnum enum1 = EnumUtil.getByValue(TestGfcEnum.class, 1);
    assertThat(enum1, is(TestGfcEnum.ELEMENT_2));

  }

  @Test(expected = NotFoundException.class)
  public void testEnumUtilException() throws Exception {

    EnumUtil.getByValue(TestGfcEnum.class, 99);

  }

  @Test
  public void testEnumUtilExceptionHierarchy_NotFoundException() throws Exception {

    try {
      EnumUtil.getByValue(TestGfcEnum.class, 99);
    } catch (NotFoundException exc) {
      // all good
      return;
    }

    fail("Should have caught the exception");

  }

  @Test
  public void testEnumUtilExceptionHierarchy_IllegalArgumentException() throws Exception {

    try {
      EnumUtil.getByValue(TestGfcEnum.class, 99);
    } catch (IllegalArgumentException exc) {
      // all good
      return;
    }

    fail("Should have caught the exception");

  }

}
