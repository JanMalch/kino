package io.github.janmalch.kino.util;

import static io.github.janmalch.kino.util.functions.FunctionUtils.uncheck;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BeanUtils {

  /**
   * Returns a stream of the properties for the given target class. If the retrieveSetters flag is
   * set to true, only property descriptors with at least a setter method will be returned. If the
   * retrieveSetters flag is set to false, only property descriptors with at least a getter method
   * will be returned.
   *
   * @param targetClass the class to retrieve methods from
   * @param retrieveSetters flag, to determine if setters or getters are wanted
   * @return Stream of PropertyDescriptor for the targetClass
   */
  public static Stream<PropertyDescriptor> streamPropertyDescriptors(
      Class<?> targetClass, boolean retrieveSetters) {
    return uncheck(
        () -> {
          var stream =
              Arrays.stream(
                  Introspector.getBeanInfo(targetClass, Object.class).getPropertyDescriptors());
          return stream.filter(
              pd -> Objects.nonNull(retrieveSetters ? pd.getWriteMethod() : pd.getReadMethod()));
        });
  }

  /**
   * Applies all data from the map to the matching setters of the target object.
   *
   * @param target the target to be updated
   * @param data the data for the target
   */
  public static void setBeanProperties(Object target, Map<String, Object> data) {
    streamPropertyDescriptors(target.getClass(), true)
        .forEach(
            pd -> {
              Object value = data.get(pd.getName());
              if (value == null) {
                return;
              }

              uncheck(() -> pd.getWriteMethod().invoke(target, value));
            });
  }

  /**
   * Creates a new instance of the targetClass. All properties from the source will be copied to the
   * target.
   *
   * @param source the source object
   * @param targetClass the target class
   * @param <T> the type of the return value
   * @return an instance of targetClass with data from the source object
   */
  public static <T> T clone(Object source, Class<T> targetClass) {
    return uncheck(
        () -> {
          var target = targetClass.getDeclaredConstructor().newInstance();
          BeanUtils.setBeanProperties(target, BeanUtils.getBeanProperties(source));
          return target;
        });
  }

  /**
   * Reads all getter methods from the given source object and returns the values in a map.
   *
   * @param source the object to read from
   * @return a map with all getter values
   * @author https://stackoverflow.com/a/8524043
   */
  public static Map<String, Object> getBeanProperties(Object source) {
    Map<String, Object> map = new HashMap<>();
    streamPropertyDescriptors(source.getClass(), false)
        .forEach(
            pd ->
                uncheck(
                    () -> {
                      Object value = pd.getReadMethod().invoke(source);
                      map.put(pd.getName(), value);
                    }));
    return map;
  }

  /**
   * Calls getBeanProperties and then removes all values which are null or it's toString is empty.
   *
   * @param source the object to read from
   * @return a map with all non-empty getter values
   * @see BeanUtils#getBeanProperties(Object)
   * @see BeanUtils#isNullOrEmpty(Object)
   */
  public static Map<String, Object> getNonEmptyBeanProperties(Object source) {
    return getBeanProperties(source)
        .entrySet()
        .stream()
        .filter(entry -> !isNullOrEmpty(entry.getValue()))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  /**
   * Returns true, if the given value is null or its string representation is empty
   *
   * @param value the value to check
   * @return if value is null or string representation is empty
   */
  public static boolean isNullOrEmpty(Object value) {
    return value == null || value.toString().trim().isEmpty();
  }
}
