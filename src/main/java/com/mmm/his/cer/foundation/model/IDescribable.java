package com.mmm.his.cer.foundation.model;

public interface IDescribable {

  /**
   * Returns the long description
   *
   * @return long description
   */
  String getLongDescription();

  /**
   * Returns the short description
   *
   * @return short description
   */
  String getShortDescription();

  /**
   * Set the long description value
   *
   * @param desc description
   */
  void setLongDescription(String desc);

  /**
   * Set the short description value
   *
   * @param desc description
   */
  void setShortDescription(String desc);
}
