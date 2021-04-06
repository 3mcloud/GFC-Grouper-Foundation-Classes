package com.mmm.his.cer.foundation;


/**
 * The release information for this domain-based GFC implementation.<br>
 * This is expected to be an implementation of an enumeration which lists all releases <i>available
 * in this dependency/build</i>.
 *
 * @author ?
 * @author Thomas Naeff
 *
 */
public interface Release extends ComponentVersion {

  /**
   * The component resource XML value of the 'component-version' tag.
   *
   * @return The 'component-version value=""'
   */
  public String getConfigurationValue();


}
