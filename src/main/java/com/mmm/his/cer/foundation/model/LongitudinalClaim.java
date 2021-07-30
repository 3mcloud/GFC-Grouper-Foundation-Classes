package com.mmm.his.cer.foundation.model;

import com.mmm.his.cer.foundation.transfer.IClaim;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Jason Flores
 */
public abstract class LongitudinalClaim implements IClaim, IClaimList {

  private static final long serialVersionUID = -6601829682577124260L;


  protected List<IClaim> claims;

  public LongitudinalClaim() {
    claims = new LinkedList<>();
  }

  /**
   * Add a single claim implementation
   *
   * @param claim implementation to add
   */
  @Override
  public void addClaim(IClaim claim) {
    claims.add(claim);
  }

  /**
   * Return a subset of claims within this container
   *
   * @param claimType claim class to return
   * @param <C>       class type of claims to return
   * @return a non-null list of claims based on the specified class type.
   */
  @SuppressWarnings("unchecked")
  public <C> List<C> getClaims(Class<C> claimType) {
    List<C> subList = new LinkedList<>();
    for (IClaim c : this.getClaims()) {
      if (claimType.isInstance(c)) {
        subList.add((C) c);
      }
    }

    return subList;
  }

  /**
   * Return all the claims within this container
   *
   * @return a non-null list of claims
   */
  @Override
  public List<IClaim> getClaims() {
    return claims;
  }

  /**
   * Set the entire list of claims on this container
   *
   * @param claims to set
   */
  @Override
  public void setClaims(List<IClaim> claims) {
    this.claims = claims;
  }
}
