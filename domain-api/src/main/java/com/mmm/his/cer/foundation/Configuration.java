package com.mmm.his.cer.foundation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Configurable properties based on a type and versioning scheme - release.
 *
 * @author a2jagzz
 *
 */
public class Configuration {

  private String driver;

  private ComponentType componentType;

  private Release release;

  private Map<String, String> resources;
  private List<String> resourcePaths;

  private List<ComponentType> dependentComponents;

  private Map<ComponentType, Configuration> dependentConfigurations;
  private Map<ComponentType, String> dependentComponentVersionMapping;

  public Configuration(ComponentType type, Release release) {
    this.setComponentType(type);
    this.setRelease(release);
  }

  /**
   * Returns the type of the component for which this configuration is loaded.
   *
   * @deprecated Use {@link #getComponentName()} instead
   * @return The type of the component for which this configuration is loaded
   */
  @Deprecated
  public ComponentType getComponentType() {
    return componentType;
  }

  /**
   * Returns the name of the component for which this configuration is loaded.
   *
   * @return The name of the component for which this configuration is loaded
   */
  public ComponentName getComponentName() {
    return componentType;
  }

  /**
   *
   * @deprecated Use {@link #getDependencies()}
   * @return
   */
  @Deprecated
  public List<ComponentType> getDependentComponents() {
    return dependentComponents;
  }

  /**
   *
   * @deprecated Use {@link #getDependenciesVersionMapping()}
   * @return
   */
  @Deprecated
  public Map<ComponentType, String> getDependentComponentVersionMapping() {
    return dependentComponentVersionMapping;
  }

  /**
   * Returns the configurations for the listed dependencies.
   *
   * @deprecated Use {@link #getDependencyConfigurations()}
   * @return The configurations for other components
   */
  @Deprecated
  public Map<ComponentType, Configuration> getDependentConfigurations() {
    return dependentConfigurations;
  }

  public List<ComponentName> getDependencies() {
    // Generate list. Can be removed once other deprecated methods are removed and list is of type
    // ComponentName
    List<ComponentName> names = new ArrayList<ComponentName>();
    for (ComponentType type : dependentComponents) {
      names.add(type);
    }
    return names;
  }

  public Map<ComponentName, String> getDependenciesVersionMapping() {
    // Generate list. Can be removed once other deprecated methods are removed and map keys are of
    // type ComponentName
    Map<ComponentName, String> mapping = new HashMap<ComponentName, String>();
    for (Entry<ComponentType, String> entry : dependentComponentVersionMapping.entrySet()) {
      mapping.put(entry.getKey(), entry.getValue());
    }
    return mapping;
  }

  public Map<ComponentName, Configuration> getDependencyConfigurations() {
    // Generate list. Can be removed once other deprecated methods are removed and map keys are of
    // type ComponentName
    Map<ComponentName, Configuration> configs = new HashMap<ComponentName, Configuration>();
    for (Entry<ComponentType, Configuration> entry : dependentConfigurations.entrySet()) {
      configs.put(entry.getKey(), entry.getValue());
    }
    return configs;
  }

  public String getDriver() {
    return driver;
  }

  public Release getRelease() {
    return release;
  }

  public Map<String, String> getResources() {
    return resources;
  }

  public void setComponentType(ComponentType componentType) {
    this.componentType = componentType;
  }

  public void setDepedentComponents(List<ComponentType> dependentComponents) {
    this.dependentComponents = dependentComponents;
  }

  public void setDependentComponentVersionMapping(
      Map<ComponentType, String> dependentComponentVersionMapping) {
    this.dependentComponentVersionMapping = dependentComponentVersionMapping;
  }

  public void
      setDependentConfigurations(Map<ComponentType, Configuration> dependentConfigurations) {
    this.dependentConfigurations = dependentConfigurations;
  }

  public void setDriver(String driver) {
    this.driver = driver;
  }

  public void setRelease(Release release) {
    this.release = release;
  }

  public void setResources(Map<String, String> resources) {
    this.resources = resources;
  }

  public List<String> getResourcePaths() {
    return resourcePaths;
  }

  public void setResourcePaths(List<String> resourcePaths) {
    this.resourcePaths = resourcePaths;
  }
}
