package com.mmm.his.cer.foundation;

/**
 * Available component types.<br />
 * The types listed here are all types available in the C&ER department. Only the implementations on
 * the classpath will actually be available.<br />
 * <br />
 *
 * @author Jason Flores
 * @author Timothy Gallagher
 * @deprecated Use a component specific {@link ComponentName} implementation instead. This
 *     enumeration will be updated until it gets removed in a future release. It is still
 *     necessary to use this type until the domain component factory is updated to use
 *     {@link ComponentName}.
 */
@Deprecated
public enum ComponentType implements ComponentName {
  /**
   * All Patient Refined.
   */
  Apr,
  /**
   * Clinical Risk Grouper.
   */
  Crg,
  /**
   * Demo component, testing and development only.
   */
  Demo,
  /**
   * Enhanced Ambulatory Patient Groups.
   */
  Eapg,
  /**
   * Functional Status Grouper.
   */
  Fsg,
  /**
   * Gapr Insight Report.
   */
  Ginr,
  /**
   * United Grouper.
   */
  Guni,
  /**
   * TRICARE grouper.
   */
  Tri,
  /**
   * AP grouper.
   */
  Ap,
  /**
   * HCPCS-DRG.
   */
  Hcpcs,
  /**
   * International Refined DRGs.
   */
  Irdrg,
  /**
   * Medicare Code Editor.
   */
  Mce,
  /**
   * MS-DRG CMS Grouper.
   */
  Msdrg,
  /**
   * Patient Focused Episodes.
   */
  Pfe,
  /**
   * Patient Focused Episodes Reference Matrix.
   */
  Pferm,
  /**
   * Population Focused Preventables.
   */
  Pfp,
  /**
   * Potentially Preventable Complications.
   */
  Ppc,
  /**
   * Present on Admission.
   */
  Poa,
  /**
   * Potentially Preventable Re-admissions.
   */
  Ppr,
  /**
   * Utility Discharge Status Mapper.
   */
  Udsm,
  /**
   * Utility Code Mapper.
   */
  Umap,
  /**
   * Value In Care.
   */
  Vic,
  /**
   * Home Health
   */
  Hhpdgm,
  /**
   * HAC Utility Grouper
   */
  Ghac,
  /**
   * CMS Outpatient Code Editor/APC Grouper
   */
  Ioce,
  /**
   * TRICARE Outpatient Code Editor/APC Grouper
   */
  Toce;

  @Override
  public String getName() {
    return name();
  }

}
