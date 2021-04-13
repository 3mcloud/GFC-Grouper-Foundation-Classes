package com.mmm.his.cer.foundation.model;

import java.io.Serializable;
import java.util.Collection;

/**
 * Provides flagging mechanism using Enums as the flag values; the Enum class can have only one
 * value set within this flag system. However, you can have many flags that implement a single
 * interface as a way of grouping the Enums. <br />
 * For example, GFC has a GfcSex enum. Only one of these values can be set within the flag
 * mechanism. However, if a group of enums implement the same interface, then many enums that
 * represent the same interface can exist within the flag list.
 *
 * @author Tim Gallagher
 */
public interface IFlags extends Serializable {

  /**
   * sets the flag to a specific enum value; there can not be two values from the same enum class,
   * so if a current enum from the same class it present it will be replace, but also returned from
   * this method
   *
   * @param enumValue
   * @return null if this is the first enum value for the specific enum class, or non-null enum of
   *         the previously set value if there is an existing value that is replaced by this method
   */
  <T extends GfcEnum> T setFlag(T enumValue);

  /**
   * removes an enum class
   *
   * @param enumClass
   * @return null if this specific enum class is not present, or non-null enum if there is an
   *         existing value
   */
  <T extends GfcEnum> T unsetFlag(Class<T> enumClass);

  /**
   * removes all flags
   */
  void clear();

  /**
   * checks for any flags present
   *
   * @return true if there are no flags
   */
  boolean isEmpty();

  /**
   * determines if an enum class is set within the flag list
   *
   * @param enumClass
   * @return true if any value of a specific enum class is found
   */
  boolean isFlagSet(Class<? extends Enum> enumClass);

  /**
   * determines if an interface class is set within the flag list
   *
   * @param interfaceClass
   * @return true if any value of a specific enum class is found
   */
  boolean isFlagTypeSet(Class interfaceClass);

  /**
   * determines if a specific enum value is present
   *
   * @param enumValue
   * @return true if the value is found
   */
  boolean isFlagValueSet(GfcEnum enumValue);

  /**
   * gets the current value of an enum class if set.
   *
   * @param enumClass
   * @return non-null if the enum class is found, otherwise null
   */
  <T extends GfcEnum> T getFlag(Class<T> enumClass);

  /**
   * gets a list, in random order of the current flags
   *
   * @return non-null list, but may be empty
   */
  Collection<GfcEnum> getFlags();

  /**
   * gets a list, in random order, of the current flags that implement the specific interface
   *
   * @param interfaceClass
   * @return non-null list, but may be empty
   */
  Collection<GfcEnum> getFlags(Class interfaceClass);

  /**
   * gets the size of the flag set
   *
   * @return zero or greater.
   */
  int size();

}
