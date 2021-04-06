package com.mmm.his.cer.foundation.model;

import java.util.Collection;
import java.util.Collections;

/**
 * Always empty and can not be added to
 *
 * @author Tim Gallagher
 */
public class FlagsEmpty implements IFlags {
  private static final long serialVersionUID = 1447243630328132872L;

  @Override
  public <T extends GfcEnum> T setFlag(T enumValue) {
    return null;
  }

  @Override
  public <T extends GfcEnum> T unsetFlag(Class<T> enumClass) {
    return null;
  }

  @Override
  public void clear() {}

  @Override
  public boolean isEmpty() {
    return true;
  }

  @Override
  public boolean isFlagSet(Class<? extends Enum> enumClass) {
    return false;
  }

  @Override
  public boolean isFlagTypeSet(Class interfaceClass) {
    return false;
  }

  @Override
  public boolean isFlagValueSet(GfcEnum enumValue) {
    return false;
  }

  @Override
  public <T extends GfcEnum> T getFlag(Class<T> enumClass) {
    return null;
  }

  @Override
  public Collection<GfcEnum> getFlags() {
    return Collections.emptyList();
  }

  @Override
  public Collection<GfcEnum> getFlags(Class interfaceClass) {
    return Collections.emptyList();
  }

  @Override
  public int size() {
    return 0;
  }
}
