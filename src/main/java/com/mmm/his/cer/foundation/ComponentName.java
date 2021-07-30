package com.mmm.his.cer.foundation;

import com.mmm.his.cer.foundation.utility.ComponentClassUtil;

/**
 * The name of the implemented component, as prefix of the {@link Processable} and other component
 * specific class implementations. See {@link ComponentClassUtil} for more information on how
 * package paths and class names are constructed<br>
 * <br>
 * This information is intended for implementation identification purposes and to build the class
 * name of various component specific implementations for dynamic loading.<br>
 * <br>
 * To customize the used package path under which a class with the given name/prefix is expected,
 * implement {@link ComponentPackage} along with this {@link ComponentName} interface.
 *
 * @author Thomas Naeff
 */
public interface ComponentName {


  /**
   * The name of the component (the prefix of the {@link Processable}, {@link ComponentVersion} and
   * other class implementations).
   *
   * @return The component name, typically in sentence case to conform to the Java standards
   */
  String getName();

}
