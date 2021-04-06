package com.mmm.his.cer.foundation.utility;

import com.mmm.his.cer.foundation.ComponentName;
import com.mmm.his.cer.foundation.ComponentPackage;
import com.mmm.his.cer.foundation.ComponentVersion;
import com.mmm.his.cer.foundation.Processable;
import com.mmm.his.cer.foundation.exception.FoundationRuntimeException;

/**
 * Some component constants and utility methods.
 *
 * @author Thomas Naeff
 *
 */
public final class ComponentClassUtil {

  private ComponentClassUtil() {
    // Only static methods and constants in here
  }

  /**
   * The standard Java package name separator used in package paths.
   */
  public static final String PACKAGE_SEPARATOR = ".";


  /**
   * The location of the component name from {@link ComponentName#getName()} in various of the
   * strings which are build with {@link ComponentClassUtil}.
   */
  private static final String COMPONENT_NAME_LOCATION = "%s";

  /**
   * The location of the component version from {@link ComponentVersion#getPackageValue()} in
   * various of the strings which are build with {@link ComponentClassUtil}.
   */
  private static final String COMPONENT_VERSION_LOCATION = "%s";

  private static final String COMPONENT_NAME_AND_VERSION_LOCATION =
      COMPONENT_NAME_LOCATION + PACKAGE_SEPARATOR + COMPONENT_VERSION_LOCATION;

  /**
   * The package path used when no custom package path is defined. This is the base path which
   * constructs the full path by appending {@link ComponentVersion#getPackageValue()}.<br>
   * This package path separates package names with {@link #PACKAGE_SEPARATOR}.
   */
  public static final ComponentPackage DEFAULT_BASE_PACKAGE_CONFIG = new ComponentPackage() {

    @Override
    public String getBasePackagePath() {
      return "com.mmm.his.cer";
    }
  };

  /**
   * The postfix for a {@link Processable} implementation. The prefix is defined in
   * {@link ComponentName#getName()}.
   */
  public static final String COMPONENT_CLASS_POSTFIX = "Component";

  /**
   * The postfix for a {@link ComponentVersion} implementation. The prefix is defined in
   * {@link ComponentName#getName()}.
   */
  public static final String VERSION_CLASS_POSTFIX = "Version";

  /**
   * Constructs the full package path for the {@link Processable} implementation. The returned path
   * should "point" to the class with the name of {@link #getComponentClassName(ComponentName)}.
   *
   * @param componentName The component name object
   * @param componentVersion The component version object
   * @return The full package path, with the package name forced to all lowercase
   */
  public static final String getComponentClassPackagePath(ComponentName componentName,
      ComponentVersion componentVersion) {
    ComponentPackage basePackageConfig = getBasePackageConfig(componentName, componentVersion);
    String basePackage = basePackageConfig.getBasePackagePath();
    if (!basePackage.endsWith(PACKAGE_SEPARATOR)) {
      basePackage += PACKAGE_SEPARATOR;
    }

    basePackage += COMPONENT_NAME_AND_VERSION_LOCATION;
    return String.format(basePackage, componentName.getName(), componentVersion.getPackageValue())
        .toLowerCase();
  }

  /**
   * Constructs the full package path for the {@link ComponentVersion} implementation. The returned
   * path should "point" to the class with the name of {@link #getVersionClassName(ComponentName)}.
   *
   * @param componentName The component name object
   * @return The full package path, with the package name forced to all lowercase
   */
  public static final String getVersionClassPackagePath(ComponentName componentName) {
    ComponentPackage basePackageConfig = getBasePackageConfig(componentName, null);
    String basePackage = basePackageConfig.getBasePackagePath();
    if (!basePackage.endsWith(PACKAGE_SEPARATOR)) {
      basePackage += PACKAGE_SEPARATOR;
    }

    basePackage += COMPONENT_NAME_LOCATION;
    return String.format(basePackage, componentName.getName()).toLowerCase();
  }

  /**
   * Builds the fully qualified class path of a {@link Processable} implementation.
   *
   * @param componentName The component name object
   * @param componentVersion The component version object
   * @return The fully qualified class path
   */
  public static final String getFullyQualifiedComponentClassPath(ComponentName componentName,
      ComponentVersion componentVersion) {
    return ComponentClassUtil.getComponentClassPackagePath(componentName, componentVersion)
        + ComponentClassUtil.PACKAGE_SEPARATOR
        + ComponentClassUtil.getClassName(componentName, COMPONENT_CLASS_POSTFIX);
  }

  /**
   * Builds the fully qualified class path of a {@link ComponentVersion} implementation.
   *
   * @param componentName The component name object
   * @return The fully qualified class path
   */
  public static final String
      getFullyQualifiedComponentVersionClassPath(ComponentName componentName) {
    return ComponentClassUtil.getVersionClassPackagePath(componentName)
        + ComponentClassUtil.PACKAGE_SEPARATOR
        + ComponentClassUtil.getClassName(componentName, VERSION_CLASS_POSTFIX);
  }

  /**
   * Constructs the full class name for a class which uses the {@link ComponentName} in it.<br>
   * <br>
   * For the postfix, see {@link #COMPONENT_CLASS_POSTFIX} or {@link #VERSION_CLASS_POSTFIX}, or use
   * your own.
   *
   * @param componentName The component name object
   * @param The postfix, appended to the name
   * @return The class name
   */
  public static final String getClassName(ComponentName componentName, String postfix) {
    String format = COMPONENT_NAME_LOCATION
        + "%s";
    return String.format(format, componentName.getName(), postfix);
  }

  /**
   * Checks if the given {@link ComponentName} and {@link ComponentVersion} objects implement
   * {@link ComponentPackage}. Returns the object cast to {@link ComponentPackage} if present,
   * otherwise returns the default {@link #DEFAULT_BASE_PACKAGE_CONFIG}. If both parameters
   * implement {@link ComponentPackage}, it checks that they are implemented the same.
   *
   * @param componentName The component name object
   * @param componentVersion The component version object
   * @return A {@link ComponentPackage}
   */
  private static final ComponentPackage getBasePackageConfig(ComponentName componentName,
      ComponentVersion componentVersion) {

    ComponentPackage fromName = null;
    if (componentName instanceof ComponentPackage) {
      fromName = (ComponentPackage) componentName;
    }

    ComponentPackage fromVersion = null;
    if (componentVersion instanceof ComponentPackage) {
      fromVersion = (ComponentPackage) componentVersion;
    }

    if (fromName != null && fromVersion != null) {
      // Compare with ignore-case because at the end the package path is forced to all lowercase
      // anyways as it is standard for Java
      if (!fromName.getBasePackagePath().equalsIgnoreCase(fromVersion.getBasePackagePath())) {
        throw new FoundationRuntimeException("The base package path implementations "
            + ComponentPackage.class.getName()
            + " for "
            + fromName.getClass().getName()
            + " and "
            + fromVersion.getClass().getName()
            + " do not match.");
      }
    }

    // Return one of them since they are both implemented the same, or return a default
    if (fromName != null) {
      return fromName;
    } else if (fromVersion != null) {
      return fromVersion;
    } else {
      return DEFAULT_BASE_PACKAGE_CONFIG;
    }
  }

}
