package com.mmm.his.cer.foundation;

import com.mmm.his.cer.foundation.utility.ComponentClassUtil;

/**
 * Release version information for the implemented and available component(s). This is expected to
 * be (and best achieved with) an enumeration implementation.<br>
 * <br>
 * This release version information is intended for build identification purposes and to build the
 * fully qualified class path to a {@link Processable} for dynamic loading. See
 * {@link ComponentClassUtil} for more information on how package paths and class names are
 * constructed.<br>
 * <br>
 * <b>Important:</b> <br>
 * <u>For multi-version dependencies:</u> The implementation of this version object should be in a
 * package on a higher level than where the versioning happens, e.g. if versioned package paths are
 * <code>com.mmm.his.abc.v123</code> and <code>*.abc.v124</code> then this version implementation
 * should be under the <code>com.mmm.his.abc</code> package and this version enum would contain
 * elements corresponding to "v123" and "v124".<br>
 * <u>For single-version dependencies:</u> This version implementation can (and probably should be
 * for running multiple releases/version in the same JVM) be located under its versioned package or
 * at least a package which distinguishes it from another release version object for other
 * version(s).<br>
 * <br>
 * To customize the used package path under which a class with the given name/prefix is expected,
 * implement {@link ComponentPackage} along with this {@link ComponentVersion} interface.
 *
 * @author Thomas Naeff
 */
public interface ComponentVersion {


  /**
   * The value used for versioning a Java class path of the {@link Processable} implementation.<br>
   * <br>
   * The return string can be the name of a single package or it can be a partial package path with
   * package names separated by {@link ComponentClassUtil#PACKAGE_SEPARATOR}.<br>
   * <br>
   *
   * @return A valid Java class package name or partial path
   * @see ComponentClassUtil
   */
  String getPackageValue();


}
