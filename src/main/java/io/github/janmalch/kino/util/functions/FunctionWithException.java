package io.github.janmalch.kino.util.functions;

public interface FunctionWithException<T, R, E extends Exception> {
  R apply(T t) throws E;
}
