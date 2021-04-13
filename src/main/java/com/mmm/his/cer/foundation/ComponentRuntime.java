package com.mmm.his.cer.foundation;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A class defining options to configure a {@link Processable}. Options will be defined as an
 * enumeration of keys (K) and String (or any {@link Object}) values.
 *
 * @author a2jagzz
 * @param <K> The option key type
 */
public abstract class ComponentRuntime<K extends Enum<K>> implements Map<K, Object>, Serializable {
  private static final long serialVersionUID = -3438262715909716998L;
  private Map<K, Object> map = new HashMap<K, Object>();

  @Override
  public void clear() {
    map.clear();
  }

  @Override
  public boolean containsKey(Object key) {
    return map.containsKey(key);
  }

  @Override
  public boolean containsValue(Object value) {
    return map.containsValue(value);
  }

  @Override
  public Set<Entry<K, Object>> entrySet() {
    return map.entrySet();
  }

  @Override
  public Object get(Object key) {
    return map.get(key);
  }

  @Override
  public boolean isEmpty() {
    return map.isEmpty();
  }

  @Override
  public Set<K> keySet() {
    return map.keySet();
  }

  @Override
  public Object put(K key, Object value) {
    return map.put(key, value);
  }

  @Override
  public void putAll(Map<? extends K, ? extends Object> values) {
    map.putAll(values);
  }

  @Override
  public Object remove(Object key) {
    return map.remove(key);
  }

  @Override
  public int size() {
    return map.size();
  }

  @Override
  public Collection<Object> values() {
    return map.values();
  }

}
