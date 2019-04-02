package io.github.janmalch.kino.util;

/**
 * DSL class to provide declarative methods to codify functions' assumptions and promises. It
 * provides three functions for capturing execution context:
 *
 * <h3><code>require(boolean, String)</code></h3>
 *
 * Used to test function arguments. Throws <code>IllegalArgumentException</code>.
 *
 * <h3><code>check(boolean, String)</code></h3>
 *
 * Used to test object state. Throws <code>IllegalStateException</code>.
 *
 * <h3><code>affirm(boolean, String)</code></h3>
 *
 * Used to clarify outcomes and check your work. Throws <code>AssertionError</code>. The meaning of
 * an <code>AssertionError</code> is that something happened that the developer thought was
 * impossible to happen.
 *
 * <h2>Example</h3>
 *
 * <pre><code>
 * public class Foo {
 *   public int x = 0;
 *
 *   public void addNonNegativeNumbers(int y) {
 *     // checks arguments
 *     Contract.require(y >= 0, "y must be non-negative, was " + y);
 *     // checks internal state
 *     Contract.check(x >= 0, "x must be non-negative, was " + x);
 *     x += y;
 *     // checks result
 *     Contract.affirm(x >= 0, "illegal state after computation: " + x);
 *   }
 * }
 *
 * </code></pre>
 */
public final class Contract {

  /**
   * Used to test function arguments.
   *
   * @param value the value that should be <code>true</code>
   * @param message description of what is expected
   * @throws IllegalArgumentException if the <code>value</code> argument is false
   */
  public static void require(boolean value, String message) throws IllegalArgumentException {
    if (!value) {
      throw new IllegalArgumentException(message);
    }
  }

  /**
   * Used to test object state.
   *
   * @param value the value that should be <code>true</code>
   * @param message description of what is expected
   * @throws IllegalStateException if the <code>value</code> argument is false
   */
  public static void check(boolean value, String message) throws IllegalStateException {
    if (!value) {
      throw new IllegalStateException(message);
    }
  }

  /**
   * Used to clarify outcomes and check your work. The meaning of an <code>AssertionError</code> is
   * that something happened that was thought to be impossible.
   *
   * @param value the value that should be <code>true</code>
   * @param message description of what is expected
   * @throws AssertionError if the <code>value</code> argument is false
   */
  public static void affirm(boolean value, String message) throws AssertionError {
    if (!value) {
      throw new AssertionError(message);
    }
  }
}
