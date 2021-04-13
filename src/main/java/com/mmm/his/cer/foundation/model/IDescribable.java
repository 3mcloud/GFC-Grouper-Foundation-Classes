package com.mmm.his.cer.foundation.model;

public interface IDescribable {

  /**
   * Returns the long description
   *
   * @return long description
   */
  public String getLongDescription();

  /**
   * Returns the short description
   *
   * @return short description
   */
  public String getShortDescription();

  /**
   * Set the long description value
   *
   * @param desc description
   */
  public void setLongDescription(String desc);

  /**
   * Set the short description value
   *
   * @param desc description
   */
  public void setShortDescription(String desc);
}
