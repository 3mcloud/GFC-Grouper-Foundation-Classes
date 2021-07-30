package com.mmm.his.cer.foundation.model;

import com.mmm.his.cer.foundation.transfer.Claim;
import com.mmm.his.cer.foundation.utility.GfcDate;

import java.io.Serializable;
import java.util.List;

/**
 * @author Jason Flores
 * @deprecated
 */
@Deprecated
public interface IPatient extends Serializable {

  String getPatientId();

  void setPatientId(String patientId);

  void setBirthDate(GfcDate birthDate);

  GfcDate getBirthDate();

  void addClaim(Claim claim);

  void setClaims(List<Claim> claims);

  List<Claim> getClaims();
}
