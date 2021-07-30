package com.mmm.his.cer.foundation;

import com.mmm.his.cer.foundation.utility.ComponentClassUtil;

/**
 * An interface to customize the base package path of the implemented component and various of its
 * classes if a different package path is needed than
 * {@link ComponentClassUtil#DEFAULT_BASE_PACKAGE_PATH}.<br>
 * <br>
 * This is an extension for {@link ComponentName} and/or {@link ComponentVersion} and has to be
 * implemented in the same class as those if {@link ComponentClassUtil} should generate a custom
 * package path based on {@link ComponentPackage#getBasePackagePath()}. It can be implemented in
 * both {@link ComponentName} and {@link ComponentVersion} (and in that case would have to return
 * the same value) or in just one of them if the other one can not be implemented.<br>
 * <br>
 * This package path is intended to build the fully qualified class path to a {@link Processable}
 * and other classes for dynamic loading. See {@link ComponentClassUtil} for more information.<br>
 *
 * @author Thomas Naeff
 */
public interface ComponentPackage {


  /**
   * The custom base package path on to which the {@link ComponentName} and {@link ComponentVersion}
   * will be appended.
   *
   * @return The base package path, with our without {@link ComponentClassUtil#PACKAGE_SEPARATOR} at
   *     the end.
   */
  String getBasePackagePath();

}
