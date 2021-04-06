package com.mmm.his.cer.foundation.utility;

import com.mmm.his.cer.foundation.ComponentName;
import com.mmm.his.cer.foundation.ComponentType;
import com.mmm.his.cer.foundation.Configuration;
import com.mmm.his.cer.foundation.Release;
import com.mmm.his.cer.foundation.exception.FoundationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Collection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Utility to write out jar resources to the file system so that they can be used at runtime.
 * Created by mfunaro
 *
 * @author Mike Funaro
 * @author Bao Tran
 * @author Tim Gallagher
 */
public class ResourceUtil {

  private static final String RESOURCE_GFC_PATH;
  private static final String CONFIG_PATH; // [componentType]Resource.xml


  static {
    // let the gfc.resource.path override the gfc path
    String path = get("gfc.resource.path", null);

    String resourcePath = null;
    if (path == null) {
      // find the temp folder on the machine
      path = get("java.io.tmpdir", null);
      resourcePath = new File(path, "gfc").getAbsolutePath();

    } else {
      resourcePath = path;
    }

    if (!resourcePath.endsWith(File.separator)) {
      resourcePath += File.separator;
    }
    RESOURCE_GFC_PATH = resourcePath;
    CONFIG_PATH = get("gfc.config.path");
  }

  private static final Logger logger = LoggerFactory.getLogger(ResourceUtil.class);

  /**
   * Extract contents of a zip file, which in our case will be a directory contains resource files.
   *
   * @param zipFile containing our resource directory.
   */
  private static void explodeZipFile(File zipFile) {
    byte[] buffer = new byte[1024];

    try {
      // create output directory is not exists
      File folder = new File(zipFile.getParent());
      if (!folder.exists()) {
        folder.mkdir();
      }

      // get the zip file content
      ZipInputStream zis = null;
      try {
        zis = new ZipInputStream(new FileInputStream(zipFile));;

        // get the zipped file list entry
        ZipEntry ze = zis.getNextEntry();

        while (ze != null) {
          String fileName = ze.getName();
          File newFile = new File(folder + File.separator + fileName);

          logger.info("file unzip : {}", newFile.getAbsoluteFile());

          // create all non exists folders
          // else you will hit FileNotFoundException for compressed folder
          new File(newFile.getParent()).mkdirs();

          FileOutputStream fos = null;
          try {
            fos = new FileOutputStream(newFile);;

            int len;
            while ((len = zis.read(buffer)) > 0) {
              fos.write(buffer, 0, len);
            }

          } finally {
            if (fos != null) {
              fos.close();
            }
          }

          ze = zis.getNextEntry();
        }

      } finally {
        if (zis != null) {
          zis.closeEntry();
          zis.close();
          // Delete the zip file, we don't need it anymore.
          zipFile.deleteOnExit();
        }
      }

      logger.info("Done");

    } catch (IOException exc) {
      // TODO handle or pass upwards
      logger.error("Failed reading archive "
          + zipFile, exc);
    }
  }

  /**
   * Simple utility method that will remove repeated extensions from the end of a file e.g. to get
   * the base name of a file called demo.h2.db, you would call this method with the filename and
   * number of extensions to remove of 2.
   *
   * @param filename file name with the extensions to remove
   * @param numberOfExtensionsToRemove number of extensions to remove to get to the basename
   * @return string representing the basename of the file.
   */
  public static String extensionRemover(String filename, int numberOfExtensionsToRemove) {
    String string = filename;
    for (int i = 0; i < numberOfExtensionsToRemove; ++i) {
      string = string.split("\\.(?=[^\\.]+$)")[0];
    }
    return string;
  }

  public static final String getConfigurationPath() {
    return CONFIG_PATH;
  }

  /**
   * Gets resource path as a string, guaranteeing that to include the ending file separator.
   *
   * @return non-null String
   */
  public static final String getResourcePath() {
    return RESOURCE_GFC_PATH;
  }

  /**
   * This is a convenience methods that calls get(String, String)
   *
   * @see String get(String overrideValue, String defaultValue)
   */
  private static final String get(String overrideValue) {
    return get(overrideValue, null);
  }

  /**
   * This retrieves the System Property by the first checking for the Override value. If that is not
   * found it uses the default value.
   *
   * @return non-null String if overrideValue or defaultValue are found within the System property
   */
  private static final String get(String overrideValue, String defaultValue) {
    String member = System.getProperty(overrideValue);
    if (member == null && defaultValue != null) {
      return System.getProperty(defaultValue);
    }
    return member;
  }

  /**
   * Write out all the resource files that are contains in the Component jar to the system temporary
   * directory.
   *
   * @param filenames to write to disk
   * @param clazz {@link Class} used to get a handle on the class loader for the component.
   * @throws FoundationException - when permission/access changes can not occur due to the
   *         underlying file system or Security manager; the exception will the tag "Security:" at
   *         the beginning of the message.
   */
  public static void writeComponentResourcesToFileSystem(Collection<String> filenames,
      Class<?> clazz, ComponentName type, Release release, File ctlPath)
      throws FoundationException {
    // Get handle on the resource dir
    File gfcDir;
    try {
      gfcDir = ctlPath != null ? ctlPath : new File(getDigestPath(filenames, clazz, type, release));
    } catch (IOException exc) {
      throw new FoundationException("Could not determine GFC folder location for storing .ctl "
          + "and other component resources", exc);
    }

    // If the directory does not exist, create it
    createDirectory(gfcDir);

    // For each of the file names we have, check if the file already exists in the gfc temp dir,
    // if not, create
    // a new file and write it.
    for (String filename : filenames) {
      // Create tmp file to see if it already exists on the file system.
      File fileToWrite = new File(gfcDir, filename);
      File digestFile = new File(fileToWrite.getAbsolutePath()
          + ".sha1");
      File zipDir = new File(gfcDir, filename.replace(".zip", ""));

      if (!zipDir.exists()) {
        if (!fileToWrite.exists() || !fileToWrite.getParentFile().exists()) {
          try {
            writeFile(clazz, fileToWrite, filename.contains(".zip"));
          } catch (IOException exc) {
            throw new FoundationException("Could not write resource file: "
                + fileToWrite.getAbsolutePath(), exc);
          }

          setRWPermissions(fileToWrite);
          try {
            writeFile(clazz, digestFile, false);
          } catch (IOException exc) {
            throw new FoundationException("Could not write digest file: "
                + fileToWrite.getAbsolutePath()
                + ".sha1", exc);
          }
          setRWPermissions(digestFile);
        }
      } else {
        // If the file exists check the meta data to make sure we are in sync and nothing is
        // corrupted.

        // Get handle to the digest file that is packaged in the jar, open and read in the digest
        String digestInResource;
        try {
          digestInResource = readDigestFile(clazz, filename, true);
        } catch (IOException exc) {
          throw new FoundationException("Could not read digest file in component jar: "
              + filename, exc);
        }

        // Get handle to the digest file on the filesystem, open and read in the digest
        String digestInLocal;
        try {
          digestInLocal = readDigestFile(clazz, fileToWrite.getAbsolutePath(), false);
        } catch (IOException exc) {
          throw new FoundationException("Could not read digest file in jar: "
              + fileToWrite.getAbsolutePath(), exc);
        }

        // Compare the digests, if checksum changed, write the file
        if (!digestInLocal.equals(digestInResource)) {
          try {
            writeFile(clazz, fileToWrite, filename.contains(".zip"));
          } catch (IOException exc) {
            throw new FoundationException("Could not write resource file: "
                + fileToWrite.getAbsolutePath(), exc);
          }
          setRWPermissions(fileToWrite);
          try {
            // Write out the newer digest string (from resource folder) to a file
            writeDigestFile(fileToWrite, digestInResource);
          } catch (FileNotFoundException exc) {
            throw new FoundationException("Could not write digest file: "
                + fileToWrite.getAbsolutePath()
                + ".sha1", exc);
          }
          setRWPermissions(fileToWrite);
        }
      }
    }
  }

  /**
   * Write the given string to the given file
   *
   * @param targetFile file to write the string to
   * @param digest the string to write to the file
   */
  private static void writeDigestFile(File targetFile, String digest) throws FileNotFoundException {
    PrintWriter output = new PrintWriter(new File(targetFile.getAbsolutePath()
        + ".sha1"));
    output.print(digest);
    output.close();
  }

  /**
   * Read content of the digest (.sha) file from the resource, or from local temp directory
   * depending on <code>isResource</code>
   *
   * @param clazz class to obtain the resources from, may be null
   * @param filename name of the fileto look for within the resources
   * @param isResource indicator to determine the location of the digest file
   * @return content of the digest file
   */
  private static String readDigestFile(Class<?> clazz, String filename, boolean isResource)
      throws IOException {
    String digest = "";
    BufferedReader bufferedReader = null;
    filename += ".sha1";
    Reader reader;

    try {
      if (!isResource) {
        reader = new FileReader(filename);
      } else {
        InputStream stream = clazz.getClassLoader().getResourceAsStream(filename);
        if (stream == null) {
          throw new IOException("Failed to locate file in classpath resource: "
              + filename);
        }
        reader = new InputStreamReader(stream);
      }

      bufferedReader = new BufferedReader(reader);
      digest = bufferedReader.readLine();

    } finally {
      if (bufferedReader != null) {
        try {
          bufferedReader.close();
        } catch (IOException exc) {
          // don't care
        }
      }
    }

    return digest;
  }

  /**
   * Method to write resource files to the temporary directory on filesystem
   *
   * @param clazz Class to be used to get a handle on the classloader
   * @param fileToWrite the file that is going to be wrtten to the filesystem
   * @param isZip is the file a zip file
   */
  private static void writeFile(Class<?> clazz, File fileToWrite, boolean isZip)
      throws IOException {
    // Get handle to resource file and write it to the filesystem.
    InputStream inputStream = clazz.getClassLoader().getResourceAsStream(fileToWrite.getName());

    if (inputStream == null) {
      throw new IOException("Failed to locate file in classpath resources: "
          + fileToWrite.getName());
    }

    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream(fileToWrite);
      byte[] dataByes = new byte[2048];
      int nread = inputStream.read(dataByes);
      while (nread != -1) {
        fos.write(dataByes, 0, nread);
        nread = inputStream.read(dataByes);
      }
    } finally {
      if (fos != null) {
        fos.close();
      }
    }
    // If we wrote out a zip file, explode it now into a directory.
    if (isZip) {
      explodeZipFile(fileToWrite);
    }
  }

  /**
   * In a shared "tmp" folder environment like Solaris or Linux, the permissions need to be set to
   * allow other users access to the folder.
   */
  public static void setRWPermissions(File file) throws FoundationException {
    try {
      if (!file.setReadable(true, false)) {
        throw new FoundationException("Security: User does not have permission to change "
            + "the Readable access permissions of this directory: "
            + file.getAbsolutePath());
      }
    } catch (SecurityException exc) {
      throw new FoundationException(
          "Security: Could not set the directory's Read permission due to security issue: "
              + file.getAbsolutePath(),
          exc);
    }
    try {
      if (!file.setWritable(true, false)) {
        throw new FoundationException("Security: User does not have permission to change "
            + "the Writable access permissions of this directory: "
            + file.getAbsolutePath());
      }
    } catch (SecurityException exc) {
      throw new FoundationException(
          "Security: Could not set the directory's Write permission due to security issue: "
              + file.getAbsolutePath(),
          exc);
    }
  }

  private static void createDirectory(File directory) throws FoundationException {
    if (!directory.exists()) {
      directory.mkdirs();
      String os = System.getProperty("os.name").toLowerCase();
      if (isUnix(os) || isSolaris(os)) {
        setRWPermissions(directory);
      }
    }
  }

  private static boolean isWindows(String os) {
    return os.contains("win");
  }

  private static boolean isMac(String os) {
    return os.contains("mac");
  }

  private static boolean isUnix(String os) {
    return os.contains("nix") || os.contains("nux") || os.indexOf("aix") > 0;
  }

  private static boolean isSolaris(String os) {
    return os.contains("sunos");
  }

  /**
   * Convenience version that uses the Configuration to get the Resource file names used in the
   * other version.
   *
   * @param configuration
   * @param type
   * @param release
   * @return non-null String
   */
  public static String getDigestPath(Configuration configuration, ComponentType type,
      Release release) throws IOException {
    return getDigestPath(configuration.getResources().keySet(), configuration.getClass(), type,
        release);
  }

  /**
   * This builds a path based on the component's type and release name. If the component's release
   * name is Legacy, it will further add a .ctl digest hash number to the path so that components
   * that have reused the Legacy release over several versions, will be able to separate their .ctl
   * files.
   *
   * @param filenames
   * @param clazz
   * @param type
   * @param release
   * @return
   */
  public static String getDigestPath(Collection<String> filenames, Class<?> clazz,
      ComponentName type, Release release) throws IOException {
    File folder = new File(RESOURCE_GFC_PATH, type.getName());
    final String releaseVal;

    if (release.getClass().isEnum()) {
      Enum<?> anEnum = (Enum<?>) release;
      releaseVal = anEnum.name();
    } else {
      throw new IllegalArgumentException("Release must be an enum.  This release is not "
          + release
          + ", release class = "
          + release.getClass().getName());
    }

    folder = new File(folder, releaseVal);

    // if the release is Legacy, then we'll add the component's digest to the
    // folder
    if ("Legacy".equalsIgnoreCase(releaseVal)) {
      // loop through the files to find the first non-null, non-empty digest
      // value and add it to the folder
      for (String filename : filenames) {
        String digestInResource = readDigestFile(clazz, filename, true);
        if (digestInResource != null && !digestInResource.isEmpty()) {
          folder = new File(folder, digestInResource);
          break;
        }
      }
    }

    return folder.getAbsolutePath() + File.separator;
  }

}
