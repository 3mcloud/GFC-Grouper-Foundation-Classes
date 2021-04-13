package com.mmm.his.cer.foundation.model;

import java.io.Serializable;

/**
 * This holds a non-null code value. However, if this class is extended, then the non-null can not
 * be guaranteed. This class does not perform any validation on length or format of the code value
 *
 * @author a2jagzz
 * @author Tim Gallagher
 */
public abstract class Code implements ICode, Serializable, IHasFlags {
  private static final long serialVersionUID = 1472323677263570520L;

  protected static final String BLANK = "";


  /**
   * The actual code value - should never null, but might be empty
   */
  protected String value;

  /**
   * Holds the flags associated with this code; this is private in order to force lazy creating of
   * the IFlags object
   */
  private IFlags flags;


  /**
   * Creates blank code value
   */
  public Code() {
    this.value = BLANK;
  }

  /**
   * sets the code value to blank if it is null, or calls the setValue() to allow overriding
   *
   * @param value
   */
  public Code(String value) {
    setValue(value);
    if (this.value == null) {
      this.value = BLANK;
    }
  }

  /**
   * Sets the code value
   *
   * @param codeValue
   */
  @Override
  public void setValue(String codeValue) {
    this.value = codeValue;
  }

  /**
   * gets the code value
   *
   * @return The value may be null
   */
  @Override
  public String getValue() {
    return value;
  }

  /**
   * same as getValue()
   *
   * @return the value
   */
  @Override
  public String toString() {
    return value;
  }

  /**
   * gets the flags associated with this code, providing late object creation until this method is
   * called.
   *
   * @return non-null IFlags
   */
  @Override
  public IFlags getFlags() {
    if (this.flags == null) {
      this.flags = new Flags();
    }
    return this.flags;
  }

  /**
   * Determines if the flags list is empty. This method ensures late object creation if there are no
   * flags yet.
   *
   * @return
   */
  @Override
  public boolean isFlagsEmpty() {
    return this.flags != null ? this.flags.isEmpty() : true;
  }



}
