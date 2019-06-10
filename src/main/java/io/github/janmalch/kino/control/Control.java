package io.github.janmalch.kino.control;

/**
 * Interface for controls in the entity-control-boundary-pattern context. Each control represents a
 * use-case in the domain. It's logic is defined in the {@link Control#execute} method.
 *
 * @param <P> type of the result
 */
public interface Control<P> {
  /**
   * Executes the control to run the defined use-case logic. Pass in a {@link ResultBuilder}
   * instance to define the desired result objects.
   *
   * @param result a {@link ResultBuilder}, that creates the result objects
   * @param <T> the type of the result object
   * @return an instance of a result object, defined by the {@link ResultBuilder}
   */
  <T> T execute(ResultBuilder<T, P> result);
}
