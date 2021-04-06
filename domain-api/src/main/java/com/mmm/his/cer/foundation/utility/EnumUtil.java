package com.mmm.his.cer.foundation.utility;

import com.mmm.his.cer.foundation.exception.NotFoundException;
import com.mmm.his.cer.foundation.model.GfcEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides ways to search for Enum based on non-standard enum indicators such as description that
 * is not the name, or char representation, or integer value that is not the ordinal or hashCode
 * value
 *
 * @author Tim Gallagher - 3M HIS CER
 * @author a2jagzz
 */
public class EnumUtil {

  private static final Logger logger = LoggerFactory.getLogger(EnumUtil.class);

  private EnumUtil() {
    // Set to private to hide constructor
  }

  /**
   * This will search a GfcEnum enum type, by searching the values and try to match the charValue
   * with the enum's charValue.
   *
   * @param <T> type of class
   * @param enumType type
   * @param charValue char value
   * @return non-null if the charValue is found in the enum
   * @throws ClassCastException if the enum type is not a GfcEnum
   * @throws NotFoundException if the value is not found in the Enum class
   */
  public static <T extends Enum<T>> T getByValue(Class<T> enumType, char charValue)
      throws ClassCastException, NotFoundException {

    // check that the enum class implements CharValue
    if (GfcEnum.class.isAssignableFrom(enumType)) {
      T enumFound = null;
      final T[] values = enumType.getEnumConstants();

      for (T curEnum : values) {
        if (charValue == ((GfcEnum) curEnum).charValue()) {
          enumFound = curEnum;
          break;
        }
      }

      if (enumFound == null) {
        throw new NotFoundException(enumType, values);
      }

      return enumFound;
    } else {
      throw new ClassCastException(enumType.getName()
          + " can not be cast to "
          + GfcEnum.class.getName());
    }
  }

  /**
   * An overload to the original method signature which encapsulates the NotFoundException and
   * returns a default value
   *
   * @param <T> type of class
   * @param enumType type
   * @param charValue char value
   * @param defaultValue to return in case of a NotFoundException
   * @return non-null if the charValue is found in the enum, otherwise the default value
   */
  public static <T extends Enum<T>> T getByValue(Class<T> enumType, char charValue,
      T defaultValue) {
    try {
      return getByValue(enumType, charValue);
    } catch (Exception nfe) {
      return defaultValue;
    }
  }

  /**
   * This will search a GfcEnum enum type, by searching the values and try to match the intValue
   * with the enum's intValue
   *
   * @param <T> type of class
   * @param enumType type
   * @param intValue int value
   * @return non-null if the charValue is found in the enum
   * @throws ClassCastException if the enum type is not a GfcEnum
   * @throws NotFoundException if the value is not found in the Enum class
   */
  public static <T extends Enum<T>> T getByValue(Class<T> enumType, int intValue)
      throws ClassCastException, NotFoundException {

    // check that the enum class implements CharValue
    if (GfcEnum.class.isAssignableFrom(enumType)) {
      T enumFound = null;
      final T[] values = enumType.getEnumConstants();

      for (T curEnum : values) {
        if (intValue == ((GfcEnum) curEnum).intValue()) {
          enumFound = curEnum;
          break;
        }
      }

      if (enumFound == null) {
        throw new NotFoundException(enumType, values);
      }

      return enumFound;
    } else {
      throw new ClassCastException(enumType.getName()
          + " can not be cast to "
          + GfcEnum.class.getName());
    }
  }

  /**
   * This will search a GfcEnum enum type, by searching the values and try to match the intValue
   * with the enum's intValue
   *
   * @param <T> type of class
   * @param enumType type
   * @param intValue int value
   * @param defaultValue to use if an exception is encountered
   * @return non-null if the charValue is found in the enum, otherwise the default value
   */
  public static <T extends Enum<T>> T getByValue(Class<T> enumType, int intValue, T defaultValue) {
    try {
      return getByValue(enumType, intValue);
    } catch (Exception nfe) {
      logger.debug("No entry found for {} in {}. Using default: {}", intValue, enumType,
          defaultValue);
      return defaultValue;
    }
  }

  /**
   * @param <T> type of class
   * @param enumType ty[e
   * @param name string value
   * @return non-null if the string value is found in the enum
   * @throws NotFoundException if the value is not found in the Enum class
   */
  public static <T extends Enum<T>> T getByName(Class<T> enumType, String name)
      throws NotFoundException {
    T enumFound = null;
    final T[] values = enumType.getEnumConstants();

    for (T curEnum : values) {
      if (name.equalsIgnoreCase(curEnum.name())) {
        enumFound = curEnum;
        break;
      }
    }

    if (enumFound == null) {
      throw new NotFoundException(enumType, values);
    }

    return enumFound;
  }

  /**
   * @param <T> type of class
   * @param enumType type
   * @param name string value
   * @param defaultValue to use if an exception is encountered
   * @return non-null if the string value is found in the enum
   */
  public static <T extends Enum<T>> T getByName(Class<T> enumType, String name, T defaultValue) {
    try {
      return getByName(enumType, name);
    } catch (NotFoundException nfe) {
      logger.debug("No entry found for {} in {}. Using default: {}", name, enumType, defaultValue);
      return defaultValue;
    }
  }
}
