package io.github.janmalch.kino.util.functions;

public interface SupplierWithException<R, E extends Exception> {
  R get() throws E;
}
