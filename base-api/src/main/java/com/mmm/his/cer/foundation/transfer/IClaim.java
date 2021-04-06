package com.mmm.his.cer.foundation.transfer;


import java.io.Serializable;

/**
 * The base interface for a claim/record.
 *
 * @author a2jagzz
 * @author a0dulzz
 */
public interface IClaim extends Serializable {

  public void setClaimId(String claimId);

  public String getClaimId();

}
