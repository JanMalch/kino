package io.github.janmalch.kino.util.functions;

public interface VoidConsumerWithException<E extends Exception> {
  void execute() throws E;
}
