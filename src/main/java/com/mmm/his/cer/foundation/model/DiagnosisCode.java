package com.mmm.his.cer.foundation.model;

import java.io.Serializable;

/**
 * Holds diagnosis code.
 *
 * @author Tim Gallagher
 * @author Nicholas DiMucci
 */
public class DiagnosisCode extends Code implements Serializable {

  private static final long serialVersionUID = 1L;
  /**
   * present on admission indicator
   */
  protected GfcPoa presentOnAdmission;

  /**
   * creates blank diagnosis and POA code
   */
  public DiagnosisCode() {
    this.presentOnAdmission = GfcPoa.E;
  }

  /**
   * sets the diagnosis code ensuring non-null value, and a POA value of <code>GfcPoa.E</code>
   *
   * @param value the code value
   */
  public DiagnosisCode(String value) {
    super(value);
    this.presentOnAdmission = GfcPoa.E;
  }

  /**
   * sets the diagnosis code ensuring non-null value, and with POA as supplied
   *
   * @param value the code value
   * @param poa   the present on admission indicator
   */
  public DiagnosisCode(String value, GfcPoa poa) {
    super(value);
    this.presentOnAdmission = poa;
  }

  /**
   * sets the diagnosis code ensuring non-null value, and with POA as supplied if it could be
   * parsed, else a POA value of <code>GfcPoa.E</code>
   *
   * @param value the code value
   * @param poa   the present on admission indicator as a <code>String</code>. This is expected to
   *              be
   *              a single char String such as <code>"Y"</code> or <code>"N"</code>. If an
   *              unsupported
   *              value is passed in (see {@link GfcPoa} for acceptable char values), the field will
   *              be
   *              set to <code>null</code>.
   * @deprecated please favor <code>public DiagnosisCode(String value, GfcPoa poa)</code>
   */
  @Deprecated
  public DiagnosisCode(String value, String poa) {
    super(value);
    setPresentOnAdmission(poa);
  }

  /**
   * gets present on admission flag
   *
   * @return the {@link GfcPoa}
   */
  public GfcPoa getPresentOnAdmissionEnum() {
    return presentOnAdmission;
  }

  /**
   * @return the present on admission flag as a <code>String</code>.
   * @deprecated please favor <code>public void getPresentOnAdmissionEnum() </code>
   */
  @Deprecated
  public String getPresentOnAdmission() {
    return String.valueOf(presentOnAdmission.charValue());
  }

  /**
   * set the present on admission flag
   *
   * @param poa the {@link GfcPoa}
   */
  public void setPresentOnAdmission(GfcPoa poa) {
    this.presentOnAdmission = poa;
  }

  /**
   * @param poa the present on admission flag as a <code>String</code>. This is expected to be a
   *            single char String such as <code>"Y"</code> or <code>"N"</code>. If an unsupported
   *            value
   *            is passed in (see {@link GfcPoa} for acceptable char values), the field will be set
   *            to
   *            <code>GfcPoa.E</code>.
   * @deprecated please favor <code>public void setPresentOnAdmission(GfcPoa poa) </code>
   */
  @Deprecated
  public void setPresentOnAdmission(String poa) {
    // Ugh, why was POA ever a String to begin with >:(
    if (poa.length() == 1) {
      this.presentOnAdmission = GfcPoa.fromChar(poa.charAt(0));
    } else {
      this.presentOnAdmission = GfcPoa.E;
    }
  }
}
