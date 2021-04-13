/**
 * (c) 3M Company 2015. Created in 2015 as an unpublished copyrighted work. This program contains
 * confidential proprietary information of 3M. Such information may not be used or reproduced
 * without prior written consent of 3M.
 */
package com.mmm.his.cer.foundation.model;

import com.mmm.his.cer.foundation.transfer.Claim;
import com.mmm.his.cer.foundation.utility.GfcDate;

import java.util.LinkedList;
import java.util.List;

/**
 * Base patient class which provides generic claim handling characteristics.
 *
 * @author a2jagzz
 * @author a0dulzz
 * @author a3bygzz
 * @deprecated
 */
@Deprecated
public abstract class Patient implements IPatient {
  private static final long serialVersionUID = 1L;

  protected String patientId;

  protected GfcDate birthDate;

  protected List<Claim> claims;

  public Patient() {
    claims = new LinkedList<Claim>();
  }

  @Override
  public void addClaim(Claim claim) {
    claims.add(claim);
  }

  @Override
  public GfcDate getBirthDate() {
    return birthDate;
  }

  /**
   * @return a non-null list of all claims associated with this patient
   */
  @Override
  public List<Claim> getClaims() {
    return claims;
  }

  /**
   * @param claimType claim class to return
   * @param <C> class type of claims to return
   * @return a non-null list of claims based on the specified class type.
   */
  @SuppressWarnings("unchecked")
  public <C> List<C> getClaims(Class<C> claimType) {
    List<C> subList = new LinkedList<C>();
    for (Claim c : this.getClaims()) {
      if (claimType.isInstance(c)) {
        subList.add((C) c);
      }
    }

    return subList;
  }

  @Override
  public String getPatientId() {
    return patientId;
  }

  @Override
  public void setBirthDate(GfcDate birthDate) {
    this.birthDate = birthDate;
  }

  @Override
  public void setClaims(List<Claim> claims) {
    this.claims = claims;
  }

  @Override
  public void setPatientId(String patientId) {
    this.patientId = patientId;
  }
}
