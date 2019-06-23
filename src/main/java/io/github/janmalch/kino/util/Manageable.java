package io.github.janmalch.kino.util;

/** Interface that marks a resource, that must be managed. */
public interface Manageable {
  /** Closes this resource. */
  void close();
}
