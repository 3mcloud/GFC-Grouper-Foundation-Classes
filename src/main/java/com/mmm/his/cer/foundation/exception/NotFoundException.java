package com.mmm.his.cer.foundation.exception;

/**
 * This is used when searching for a 'value' within a 'source' object, but the value is not found.
 *
 * @author Tim Gallagher - 3M HIS CER
 */
@Deprecated
public class NotFoundException extends IllegalArgumentException {
  private static final long serialVersionUID = 1L;

  /**
   * This is the object that is being searched
   */
  private Object source;

  /**
   * This is the value being searched for within the source, but as not found
   */
  private Object value;

  /**
   * default - not very useful
   */
  public NotFoundException() {}

  /**
   * This creates an exception with a source and value, and creating a default message of:
   * "Searching " + source + " for value " + value + " but not found."
   *
   * @param source - should not be null
   * @param value - should not be null
   */
  public NotFoundException(Object source, Object value) {
    this(source, value, "Searching "
        + source
        + " for value "
        + value
        + " but not found.", null);
  }

  public NotFoundException(Object source, Object value, String message) {
    this(source, value, message, null);
  }

  public NotFoundException(Object source, Object value, String message, Throwable cause) {
    super(message, cause);
    this.source = source;
    this.value = value;
  }

  public NotFoundException(Object source, Object value, Throwable cause) {
    this(source, value, "Searching "
        + source
        + " for value "
        + value
        + " but not found.", cause);
  }

  public Object getSource() {
    return source;
  }

  public Object getValue() {
    return value;
  }
}
