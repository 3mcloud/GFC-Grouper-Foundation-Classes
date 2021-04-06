package com.mmm.his.cer.foundation;

import com.mmm.his.cer.foundation.transfer.IClaim;
import com.mmm.his.cer.foundation.utility.ResourceUtil;

import java.io.File;
import java.io.IOException;

/**
 * This abstract component definition represents a Domain language generated Java implementation in
 * GFC. It contains concepts of CTL/ROT file location and a configuration to specify which CTL/ROT
 * files to use.<br />
 * This {@link Component} is meant to be extending a {@link Processable} in order to enhance the
 * {@link Processable} for Domain language Java components processing.
 *
 * @param <I>
 * @param <K>
 * @param <O>
 *
 * @author a2jagzz
 * @author a0dulzz
 * @author a3bygzz
 *
 */
public abstract class Component<I extends IClaim, K extends Enum<K>, O extends ComponentRuntime<K>>
    implements Processable<I, K, O> {

  /**
   * Component specific configuration
   */
  protected Configuration configuration;

  /**
   * the path to the external .ctl and other resources files and set when the component is created,
   * but may be null.
   */
  protected File ctlPath;

  /**
   * Basic constructor with a configuration
   *
   * @param configuration non-null configuration
   */
  public Component(Configuration configuration) {
    this.configuration = configuration;
  }

  /**
   * Sets the configuration and ctlPath.
   *
   * @param configuration non-null Configuration
   * @param ctlPath may be null
   */
  public Component(Configuration configuration, File ctlPath) {
    this.configuration = configuration;
    this.ctlPath = ctlPath;
  }

  /**
   * gets the ctl base path which was supplied during Component creation. If the path was not
   * supplied during creation, this value will remain null.
   *
   * @return non-null only if the value is provided during Component creation, otherwise null.
   */
  public File getCtlPath() {
    return ctlPath;
  }

  /**
   * @param ctlPath
   */
  public void setCtlPath(File ctlPath) {
    this.ctlPath = ctlPath;
  }

  /**
   * Helper method that will build a file path to the resources on the file system that the
   * component needs; if the <code>ctlPath</code> was provided during component creation, then that
   * path is returned instead.
   *
   * @since 1.0
   * @param filename to use in path
   * @return a resource file path
   */
  protected String buildResourcePath(String filename) {
    File file;

    if (this.ctlPath != null) {
      file = new File(this.ctlPath, filename);
    } else {
      String path;

      try {
        path = ResourceUtil.getDigestPath(this.configuration, this.configuration.getComponentType(),
            this.configuration.getRelease());
      } catch (IOException exc) {
        // TODO: mabye push this up to the next level, but we need to determine the
        // components that it will effect or if using the base GFC folder is sufficient.
        path = ResourceUtil.getResourcePath();
      }

      file = new File(path, filename);
    }
    return file.getAbsolutePath();
  }

  protected abstract void initialize() throws Exception;
}
