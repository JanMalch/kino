package io.github.janmalch.kino.util;

/**
 * @param <S> the source type
 * @param <R> the resulting type
 */
public interface Mapper<S, R> {

  /**
   * Creates a new instance, based on the data of the existing object. Afterwards all non-empty
   * properties from the update object will be applied to the new instance. Finally all supplied
   * values will overwrite those in the new instance.
   *
   * @param update the update data, may be partial
   * @param existing the existing object with the initial values
   * @return an instance of the same type as the existing
   * @see BeanUtils#isNullOrEmpty(Object)
   */
  default R update(S update, R existing) {
    throw new UnsupportedOperationException();
  }

  /**
   * Creates a new instance, based on the data of the existing source object. Finally all supplied
   * values will overwrite those in the new instance.
   *
   * @param source the source object
   * @return an instance of the given targetClass, with the values from the source object
   */
  default R map(S source) {
    throw new UnsupportedOperationException();
  }
}
