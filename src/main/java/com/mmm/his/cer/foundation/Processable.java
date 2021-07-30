package com.mmm.his.cer.foundation;

import com.mmm.his.cer.foundation.transfer.IClaim;
import com.mmm.his.cer.foundation.utility.ComponentClassUtil;

import java.io.Closeable;

/**
 * An interface defining the minimum requirements of a Clinical &amp; Economic Research (C&amp;ER)
 * Component. All processable components will provide a {@link #process(IClaim)}, {@link #close()},
 * and {@link #reconfigure(ComponentRuntime)} method.<br>
 * <br>
 * Any implementation of a {@link Processable} should follow the package and class naming matching
 * the {@link ComponentVersion} and {@link ComponentName}. See {@link ComponentClassUtil} on how
 * class paths are constructed.
 *
 * @param <I> claim type implementation
 * @param <K> enumerated options
 * @param <O> ComponentRuntime implementation based on enumerated K
 * @author Jason Flores
 */
public interface Processable<I extends IClaim, K extends Enum<K>, O extends ComponentRuntime<K>>
    extends Closeable {

  /**
   * The main method call to invoke component logic.
   *
   * @param inputAndOutput claim instance which contains input and output values
   * @since 1.0
   */
  void process(I inputAndOutput);

  /**
   * @param option under which the current component should be constrained
   * @since 1.0
   */
  void reconfigure(O option);

}
