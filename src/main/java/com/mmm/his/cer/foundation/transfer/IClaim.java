package com.mmm.his.cer.foundation.transfer;


import java.io.Serializable;

/**
 * The base interface for a claim/record.
 *
 * @author Jason Flores
 * @author Timothy Gallagher
 */
public interface IClaim extends Serializable {

  void setClaimId(String claimId);

  String getClaimId();

}
