package com.mmm.his.cer.foundation.model;

import com.mmm.his.cer.foundation.transfer.IClaim;

import java.util.List;

/**
 *
 * @author a2jagzz
 *
 */
public interface IClaimList {

  public void addClaim(IClaim claim);

  public List<IClaim> getClaims();

  public void setClaims(List<IClaim> claims);

}
