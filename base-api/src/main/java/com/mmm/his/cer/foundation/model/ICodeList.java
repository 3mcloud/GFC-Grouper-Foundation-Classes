package com.mmm.his.cer.foundation.model;

import java.util.List;

/**
 *
 * @author a2jagzz
 * @author a0dulzz
 */
public interface ICodeList {
  /**
   * Add a code
   *
   * @param code implementing the ICode interface
   */
  public void addCode(ICode code);

  /**
   * @return all associated codes
   */
  public List<ICode> getCodes();

  /**
   * @param codeType class type of codes to return
   * @param <C> class type of codes to return
   * @return a list of codes based on their class designation
   */
  public <C> List<C> getCodes(Class<C> codeType);

}
