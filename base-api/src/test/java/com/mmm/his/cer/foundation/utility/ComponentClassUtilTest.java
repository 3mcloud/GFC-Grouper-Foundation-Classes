package com.mmm.his.cer.foundation.utility;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.mmm.his.cer.foundation.ComponentName;
import com.mmm.his.cer.foundation.ComponentPackage;
import com.mmm.his.cer.foundation.ComponentType;
import com.mmm.his.cer.foundation.ComponentVersion;
import com.mmm.his.cer.foundation.exception.FoundationRuntimeException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 *
 * @author Thomas Naeff
 *
 */
public class ComponentClassUtilTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static final ComponentName componentName = new ComponentName() {

    @Override
    public String getName() {
      return "Test";
    }
  };

  private static final ComponentName componentNameWithCustomPackage =
      new ComponentNameWithCustomPackage();

  private static final ComponentVersion componentVersion = new ComponentVersion() {

    @Override
    public String getPackageValue() {
      return "1_2_0";
    }
  };

  private static final ComponentVersion componentVersionWithCustomPackage =
      new ComponentVersionWithCustomPackage();
  private static final ComponentVersion componentVersionWithCustomPackage2 =
      new ComponentVersionWithCustomPackage2();



  @Test
  public void testNameAsClass() throws Exception {
    String result = ComponentClassUtil.getClassName(componentName, "SomeClassPostfix");
    assertThat(result, is("TestSomeClassPostfix"));
  }


  @Test
  public void testNameAsComponentTypeEnum() throws Exception {
    String result = ComponentClassUtil.getClassName(ComponentType.Demo, "SomeClassPostfix");
    assertThat(result, is("DemoSomeClassPostfix"));
  }

  @Test
  public void testComponentClassPackagePath() throws Exception {
    String result =
        ComponentClassUtil.getComponentClassPackagePath(componentName, componentVersion);
    assertThat(result, is("com.mmm.his.cer.test.1_2_0"));
  }

  @Test
  public void testVersionClassPackagePath() throws Exception {
    String result = ComponentClassUtil.getVersionClassPackagePath(componentName);
    assertThat(result, is("com.mmm.his.cer.test"));
  }

  @Test
  public void testComponentClassPackagePath_customPackage() throws Exception {
    String result = ComponentClassUtil.getComponentClassPackagePath(componentNameWithCustomPackage,
        componentVersion);
    assertThat(result, is("custom.package.path.test.1_2_0"));
  }

  @Test
  public void testVersionClassPackagePath_customPackage() throws Exception {
    String result = ComponentClassUtil.getVersionClassPackagePath(componentNameWithCustomPackage);
    assertThat(result, is("custom.package.path.test"));
  }

  @Test
  public void testComponentClassPackagePath_customPackageInBoth() throws Exception {
    String result = ComponentClassUtil.getComponentClassPackagePath(componentNameWithCustomPackage,
        componentVersionWithCustomPackage);
    assertThat(result, is("custom.package.path.test.1_2_0"));
  }

  @Test
  public void testComponentClassPackagePath_customPackageInBoth_confilict() throws Exception {

    thrown.expect(FoundationRuntimeException.class);
    thrown.expectMessage(
        "The base package path implementations com.mmm.his.cer.foundation.ComponentPackage for "
            + "com.mmm.his.cer.foundation.utility.ComponentClassUtilTest$ComponentNameWithCustomPackage "
            + "and com.mmm.his.cer.foundation.utility.ComponentClassUtilTest$ComponentVersionWithCustomPackage2 "
            + "do not match.");
    String result = ComponentClassUtil.getComponentClassPackagePath(componentNameWithCustomPackage,
        componentVersionWithCustomPackage2);
    assertThat(result, is("custom.package.path.test.1_2_0"));
  }

  /******************************************************************************************************
   *
   *
   *
   */
  private static class ComponentNameWithCustomPackage implements ComponentName, ComponentPackage {

    @Override
    public String getName() {
      return "Test";
    }

    @Override
    public String getBasePackagePath() {
      return "custom.package.path";
    }
  }

  /******************************************************************************************************
  *
  *
  *
  */
  private static class ComponentVersionWithCustomPackage
      implements ComponentVersion, ComponentPackage {

    @Override
    public String getPackageValue() {
      return "1_2_0";
    }

    @Override
    public String getBasePackagePath() {
      return "custom.package.path";
    }
  }

  /******************************************************************************************************
  *
  *
  *
  */
  private static class ComponentVersionWithCustomPackage2
      implements ComponentVersion, ComponentPackage {

    @Override
    public String getPackageValue() {
      return "x_x_x";
    }

    @Override
    public String getBasePackagePath() {
      return "package.path.different.than.in.component.name.class";
    }
  }

}
