package io.github.janmalch.kino.util;

import java.util.Collections;
import java.util.Map;

/**
 * @param <S> the source type
 * @param <R> the resulting type
 */
public interface Mapping<S, R> {

  /** @see Mapping#update(Object, Object, Class, Map) */
  default R update(S update, R existing, Class<R> targetClass) {
    return this.update(update, existing, targetClass, Collections.emptyMap());
  }

  /**
   * Creates a new instance, based on the data of the existing object. Afterwards all non-empty
   * properties from the update object will be applied to the new instance. Finally all supplied
   * values will overwrite those in the new instance.
   *
   * @param update the update data, may be partial
   * @param existing the existing object with the initial values
   * @param targetClass the class of the target object
   * @param supplies additional supplied values that will overwrite
   * @return an instance of the same type as the existing
   * @see BeanUtils#isNullOrEmpty(Object)
   */
  default R update(S update, R existing, Class<R> targetClass, Map<String, Object> supplies) {
    throw new UnsupportedOperationException();
  }

  /** @see Mapping#map(Object, Class, Map) */
  default R map(S source, Class<R> targetClass) {
    return this.map(source, targetClass, Collections.emptyMap());
  }

  /**
   * Creates a new instance, based on the data of the existing source object. Finally all supplied
   * values will overwrite those in the new instance.
   *
   * @param source the source object
   * @param targetClass the class of the target object
   * @param supplies additional supplied values that will overwrite
   * @return an instance of the given targetClass, with the values from the source object
   */
  default R map(S source, Class<R> targetClass, Map<String, Object> supplies) {
    throw new UnsupportedOperationException();
  }
}
