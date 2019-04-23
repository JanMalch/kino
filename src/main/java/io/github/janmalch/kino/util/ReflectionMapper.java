package io.github.janmalch.kino.util;

public class ReflectionMapper {

  public <S, R> R update(S update, R existing, Class<R> targetClass) {
    // clone the existing into the target
    var target = BeanUtils.clone(existing, targetClass);
    // get updated props (not null or empty)
    var updatedProps = BeanUtils.getNonEmptyBeanProperties(update);
    BeanUtils.setBeanProperties(target, updatedProps);
    return target;
  }

  public <S, R> R map(S source, Class<R> targetClass) {
    return BeanUtils.clone(source, targetClass);
  }
}
