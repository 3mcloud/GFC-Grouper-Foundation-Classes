package com.mmm.his.cer.foundation.model;

/**
 * Defines a mechanism to get flags associated with a code or grouper
 *
 * @author Tim Gallagher
 */
public interface IHasFlags {

  /**
   * gets the flags; to check for number of flags, use IFlags.isEmpty()
   *
   * @return non-null IFlags.
   */
  IFlags getFlags();

  /**
   * determines if the items has any flags associated
   *
   * @return true if the flag list is empty
   */
  boolean isFlagsEmpty();

}
