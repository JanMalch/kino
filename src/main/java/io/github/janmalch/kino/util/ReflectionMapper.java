package io.github.janmalch.kino.util;

import java.util.Collections;
import java.util.Map;

public class ReflectionMapper {

  /** @see ReflectionMapper#update(Object, Object, Class, Map) */
  public <S, R> R update(S update, R existing, Class<R> targetClass) {
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
   * @param <S> the source type
   * @param <R> the resulting type
   * @return an instance of the same type as the existing
   * @see BeanUtils#isNullOrEmpty(Object)
   */
  public <S, R> R update(S update, R existing, Class<R> targetClass, Map<String, Object> supplies) {
    // clone the existing into the target
    var target = BeanUtils.clone(existing, targetClass);
    // get updated props (not null or empty)
    var updatedProps = BeanUtils.getNonEmptyBeanProperties(update);
    BeanUtils.setBeanProperties(target, updatedProps);
    BeanUtils.setBeanProperties(target, supplies);
    return target;
  }

  /** @see ReflectionMapper#map(Object, Class, Map) */
  public <S, R> R map(S source, Class<R> targetClass) {
    return this.map(source, targetClass, Collections.emptyMap());
  }

  /**
   * Creates a new instance, based on the data of the existing source object. Finally all supplied
   * values will overwrite those in the new instance.
   *
   * @param source the source object
   * @param targetClass the class of the target object
   * @param supplies additional supplied values that will overwrite
   * @param <S> the source type
   * @param <R> the resulting type
   * @return an instance of the given targetClass, with the values from the source object
   */
  public <S, R> R map(S source, Class<R> targetClass, Map<String, Object> supplies) {
    var result = BeanUtils.clone(source, targetClass);
    BeanUtils.setBeanProperties(result, supplies);
    return result;
  }
}
