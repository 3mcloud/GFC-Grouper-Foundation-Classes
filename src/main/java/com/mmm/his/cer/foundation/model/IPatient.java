package com.mmm.his.cer.foundation.model;

import com.mmm.his.cer.foundation.transfer.Claim;
import com.mmm.his.cer.foundation.utility.GfcDate;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author a2jagzz
 * @deprecated
 */
@Deprecated
public interface IPatient extends Serializable {

  public String getPatientId();

  public void setPatientId(String patientId);

  public void setBirthDate(GfcDate birthDate);

  public GfcDate getBirthDate();

  public void addClaim(Claim claim);

  public void setClaims(List<Claim> claims);

  public List<Claim> getClaims();
}
