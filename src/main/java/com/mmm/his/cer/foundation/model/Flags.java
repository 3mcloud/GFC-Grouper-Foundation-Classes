package com.mmm.his.cer.foundation.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tim Gallagher
 */
@SuppressWarnings("unchecked")
public class Flags implements IFlags {

  private static final long serialVersionUID = -7112681615816415386L;

  private final Map<Class<?>, GfcEnum> flagMap;

  /**
   * empty, no-op flags mechanism
   */
  public static final IFlags NULL_FLAGS = new FlagsEmpty();

  public Flags() {
    this.flagMap = new HashMap<>();
  }

  @Override
  public <T extends GfcEnum> T setFlag(T enumValue) {
    T oldEnum;
    Class<? extends GfcEnum> enumClass;

    enumClass = enumValue.getClass();
    //noinspection unchecked
    oldEnum = (T) this.flagMap.get(enumClass);
    this.flagMap.put(enumClass, enumValue);

    return oldEnum;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T extends GfcEnum> T unsetFlag(Class<T> enumClass) {
    return (T) this.flagMap.remove(enumClass);

  }

  @Override
  public void clear() {
    this.flagMap.clear();
  }

  @Override
  public boolean isEmpty() {
    return this.flagMap.isEmpty();
  }

  @Override
  public boolean isFlagSet(Class<? extends Enum<?>> enumClass) {
    return this.flagMap.containsKey(enumClass);
  }

  @Override
  public boolean isFlagTypeSet(Class<?> interfaceClass) {
    for (Class<?> clazz : this.flagMap.keySet()) {
      if (clazz.isInstance(interfaceClass)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean isFlagValueSet(GfcEnum enumValue) {
    return this.flagMap.containsValue(enumValue);
  }

  @Override
  public <T extends GfcEnum> T getFlag(Class<T> enumClass) {
    return (T) this.flagMap.get(enumClass);
  }

  @Override
  public Collection<GfcEnum> getFlags() {
    return this.flagMap.values();
  }

  @Override
  public Collection<GfcEnum> getFlags(Class<?> interfaceClass) {
    ArrayList<GfcEnum> list = new ArrayList<>();

    for (Class<?> clazz : this.flagMap.keySet()) {
      if (interfaceClass.isAssignableFrom(clazz)) {
        list.add(this.flagMap.get(clazz));
      }
    }
    return list;
  }

  @Override
  public int size() {
    return this.flagMap.size();
  }

}
