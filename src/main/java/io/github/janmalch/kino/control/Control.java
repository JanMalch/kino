package io.github.janmalch.kino.control;

public interface Control<P> {
  <T> T execute(ResultBuilder<T, P> result);
}
