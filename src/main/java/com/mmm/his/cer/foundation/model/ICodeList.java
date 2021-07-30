package com.mmm.his.cer.foundation.model;

import java.util.List;

/**
 * @author Jason Flores
 * @author Timothy Gallagher
 */
public interface ICodeList {

  /**
   * Add a code
   *
   * @param code implementing the ICode interface
   */
  void addCode(ICode code);

  /**
   * @return all associated codes
   */
  List<ICode> getCodes();

  /**
   * @param codeType class type of codes to return
   * @param <C>      class type of codes to return
   * @return a list of codes based on their class designation
   */
  <C> List<C> getCodes(Class<C> codeType);

}
