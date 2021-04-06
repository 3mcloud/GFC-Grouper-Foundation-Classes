package com.mmm.his.cer.foundation.transfer;

import com.mmm.his.cer.foundation.ComponentType;
import com.mmm.his.cer.foundation.model.ICode;
import com.mmm.his.cer.foundation.model.ICodeList;

import java.util.LinkedList;
import java.util.List;

/**
 * Abstract class defining the base characteristics all claims should have.
 *
 *
 * @author a2jagzz
 * @author a0dulzz
 * @author a3bygzz
 * @author a30w4zz
 */
public abstract class Claim implements IClaim, ICodeList {
  private static final long serialVersionUID = -1604780616863716472L;

  /**
   * Claim identifier
   */
  protected String claimId;

  /**
   * Generic list of codes associated with a claim
   */
  protected List<ICode> codes;

  /**
   * Component type this claim is associated with. This is a leftover field from when the GFC
   * project was not split up and the ComponentFactory and other code was in one single project.
   */
  @Deprecated
  protected ComponentType type;

  /**
   * A new claim.<br />
   * This constructor only remains for backwards compatibility. See the constructor which does not
   * use the {@link ComponentType} as parameter.
   *
   * @param type of Component to associate with
   * @deprecated Use the constructor without {@link ComponentType}. {@link ComponentType} will be
   *             removed in future releases.
   */
  @Deprecated
  public Claim(ComponentType type) {
    this.type = type;
    codes = new LinkedList<ICode>();
  }

  /**
   * A new claim.
   *
   */
  public Claim() {
    this(null);
  }


  @Override
  public void addCode(ICode code) {
    codes.add(code);
  }

  @Override
  public String getClaimId() {
    return claimId;
  }

  @Override
  public List<ICode> getCodes() {
    return codes;
  }

  /**
   * @param codeType code class to return
   * @return a non-null list of codes based on the specified class type.
   */
  @Override
  public <C> List<C> getCodes(Class<C> codeType) {
    List<C> subList = new LinkedList<C>();
    for (ICode c : this.getCodes()) {
      if (codeType.isInstance(c)) {
        @SuppressWarnings("unchecked")
        C type = (C) c;
        subList.add(type);
      }
    }

    return subList;
  }

  /**
   * Get the type of component a claim is applicable to
   *
   * @return the enumerated type association
   * @deprecated The {@link ComponentType} class will be removed in future releases
   */
  @Deprecated
  public ComponentType getComponentType() {
    return type;
  }

  @Override
  public void setClaimId(String claimId) {
    this.claimId = claimId;
  }

  public void setCodes(List<ICode> codes) {
    this.codes = codes;
  }
}
