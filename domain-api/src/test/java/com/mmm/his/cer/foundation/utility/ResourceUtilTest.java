package com.mmm.his.cer.foundation.utility;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.mmm.his.cer.foundation.ComponentType;
import com.mmm.his.cer.foundation.demo.DemoRelease;
import com.mmm.his.cer.foundation.exception.FoundationException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bao Tran
 * @author Tim Gallagher
 */
public class ResourceUtilTest {

  List<String> fileNames;
  String resourcePath;

  @Before
  public void setUp() throws Exception {
    fileNames = new ArrayList<String>();
    fileNames.add("component.ctl");
    fileNames.add("support.ctl");

    resourcePath = ResourceUtil.getResourcePath();
  }


  @Test
  public void writeComponentResourcesToFileSystem() throws IOException, FoundationException {

    String digestPath = resourcePath
        + "Umap\\_08_0_0"
        + File.separator;
    File resourceDirectory = new File(digestPath);
    assertTrue(deleteDirectory(resourceDirectory));

    ResourceUtil.writeComponentResourcesToFileSystem(fileNames, ResourceUtilTest.class,
        ComponentType.Umap, DemoRelease._08_0_0, null);

    String factoryPath = ResourceUtil.getDigestPath(fileNames, ResourceUtilTest.class,
        ComponentType.Umap, DemoRelease._08_0_0);
    assertTrue("Digest Paths for v80 don't match: "
        + digestPath
        + " != "
        + factoryPath, digestPath.equalsIgnoreCase(factoryPath));
    assertTrue(resourceDirectory.exists());
    assertEquals(resourceDirectory.listFiles().length, 4);
  }


  @Test
  public void writeComponentResourcesToFileSystemLegacy() throws IOException, FoundationException {
    final String componentDigest = "006da168413b0da2random108ccd315a82a88efd";

    String digestPath = resourcePath
        + "Umap\\legacy\\"
        + componentDigest
        + File.separator;
    File resourceDirectory = new File(digestPath);
    assertTrue(deleteDirectory(resourceDirectory));

    ResourceUtil.writeComponentResourcesToFileSystem(fileNames, ResourceUtilTest.class,
        ComponentType.Umap, DemoRelease.LEGACY, null);

    String factoryPath = ResourceUtil.getDigestPath(fileNames, ResourceUtilTest.class,
        ComponentType.Umap, DemoRelease.LEGACY);
    assertTrue("Digest Paths for Legacy don't match: "
        + digestPath
        + " != "
        + factoryPath, digestPath.equalsIgnoreCase(factoryPath));
    assertTrue("Path does not exist: "
        + resourceDirectory, resourceDirectory.exists());
    assertEquals(resourceDirectory.listFiles().length, 4);
  }

  /**
   * Given a directory, empty its files first before deleting the directory itself
   *
   * @param directory the directory to delete
   * @return true/false if directory is deleted
   */
  private boolean deleteDirectory(File directory) {
    if (!directory.exists()) {
      return true; // Don't need to do anything
    }
    if (directory.exists() && directory.isDirectory()) {
      for (File file : directory.listFiles()) {
        file.delete();
      }
      return directory.delete();
    }

    return false;
  }

}
