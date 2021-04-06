package com.mmm.his.cer.foundation.utility;

import com.mmm.his.cer.foundation.Release;

/**
 * This provides methods common to all Release interface implementors without having them to
 * implement generic methods
 *
 *
 * @author Tim Gallagher - 3M HIS CER
 */
public class ReleaseUtil {

  private ReleaseUtil() {
    // Set to private to hide constructor
  }

  public static <T extends Enum<T>> T getReleaseByPackageName(Class<T> clazz, String packageName) {
    Release release = null;

    /*
     * check for Release related Enum
     */
    if (Release.class.isAssignableFrom(clazz)) {
      // get the enum constants as an array and call the other method
      final Release[] releases = (Release[]) clazz.getEnumConstants();
      release = getReleaseByPackageName(releases, packageName);
    } else {
      throw new IllegalArgumentException(
          "getReleaseByPackageName(Enum, String) - the Enum must implement the interface "
              + Release.class.getName());
    }
    return (T) release;
  }

  /**
   * Loop through the Release array to find the release with the same package value (name), using
   * case insensitive search
   *
   * @param releases to...
   * @param packageName to...
   * @return Release object or null if no
   */
  public static Release getReleaseByPackageName(Release[] releases, String packageName) {
    Release releaseFound = null;

    for (int idx = 0; idx < releases.length && releaseFound == null; idx++) {
      if (packageName.equalsIgnoreCase(releases[idx].getPackageValue())) {
        releaseFound = releases[idx];
      }
    }

    return releaseFound;
  }
}
