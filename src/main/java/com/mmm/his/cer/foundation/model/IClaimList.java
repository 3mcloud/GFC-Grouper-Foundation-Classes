package com.mmm.his.cer.foundation.model;

import com.mmm.his.cer.foundation.transfer.IClaim;

import java.util.List;

/**
 * @author Jason Flores
 */
public interface IClaimList {

  void addClaim(IClaim claim);

  List<IClaim> getClaims();

  void setClaims(List<IClaim> claims);

}
