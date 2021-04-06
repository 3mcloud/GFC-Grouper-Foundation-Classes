package com.mmm.his.cer.foundation;

import static org.junit.Assert.assertNotNull;

import com.mmm.his.cer.foundation.transfer.Claim;
import com.mmm.his.cer.guni.transfer.GuniClaim;
import com.mmm.his.cer.msdrg.transfer.MsdrgClaim;
import org.junit.Test;

/**
 * Tests the claim creation and other operations from an existing GFC wrapper implementation. The
 * dependency is pulled in via maven, all GFC stuff should be excluded from the dependency so that
 * all the GFC stuff gets replaced with the current state of this project.<br />
 * <br />
 * This is to ensure backwards compatibility. It only is valuable if the dependency is build with an
 * older GFC version.
 *
 * @author Thomas Naeff
 *
 */
public class BackwardsCompatibilityTest {


  @Test
  public void testGuniClaim() throws Exception {

    SomeClaimCompiledWithThisGfc claim1 = new SomeClaimCompiledWithThisGfc();
    assertNotNull(claim1);

    // Claim compiled with older GFC
    GuniClaim claim = new GuniClaim();
    assertNotNull(claim);

  }


  @Test
  public void testMsdrgClaim() throws Exception {

    // Claim compiled with older GFC
    MsdrgClaim claim = new MsdrgClaim();
    assertNotNull(claim);

  }



  /***************************************************************************************
   *
   *
   * @author Thomas Naeff
   *
   */
  private static class SomeClaimCompiledWithThisGfc extends Claim {
    private static final long serialVersionUID = -1792912774132461299L;

    public SomeClaimCompiledWithThisGfc() {
      super(ComponentType.Guni);

    }

  }


}
