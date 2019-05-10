package io.github.janmalch.kino.util;

public class ReflectionMapper<S, R> implements Mapping<S, R> {

  private final Class<R> targetClass;

  public ReflectionMapper(Class<R> targetClass) {
    this.targetClass = targetClass;
  }

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
  public R update(S update, R existing) {
    // clone the existing into the target
    var target = BeanUtils.clone(existing, targetClass);
    // get updated props (not null or empty)
    var updatedProps = BeanUtils.getNonEmptyBeanProperties(update);
    BeanUtils.setBeanProperties(target, updatedProps);
    return target;
  }

  /**
   * Creates a new instance, based on the data of the existing source object. Finally all supplied
   * values will overwrite those in the new instance.
   *
   * @param source the source object
   * @return an instance of the given targetClass, with the values from the source object
   */
  public R map(S source) {
    return BeanUtils.clone(source, targetClass);
  }

  @Override
  public String toString() {
    return "ReflectionMapper{" + "targetClass=" + targetClass + '}';
  }
}
