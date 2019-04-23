package io.github.janmalch.kino.util.functions;

import java.util.function.Function;

public class FunctionUtils {

  public static <T, R, E extends Exception> Function<T, R> uncheck(
      FunctionWithException<T, R, E> function) {
    return input -> {
      try {
        return function.apply(input);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    };
  }

  public static <R, E extends Exception> R uncheck(SupplierWithException<R, E> supplier) {
    try {
      return supplier.get();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static <E extends Exception> void uncheck(VoidConsumerWithException<E> consumer) {
    try {
      consumer.execute();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
