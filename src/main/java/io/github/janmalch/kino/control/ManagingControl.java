package io.github.janmalch.kino.control;

import io.github.janmalch.kino.util.Manageable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * A class that automatically manages resource, registered within a subclass.
 *
 * @param <P> type of the result
 */
public abstract class ManagingControl<P> implements Control<P>, Manageable {

  private final Set<Manageable> resources = new HashSet<>();

  /**
   * Executes the control to run the defined use-case logic. Pass in a {@link ResultBuilder}
   * instance to define the desired result objects. Will be called by the {@link
   * ManagingControl#execute(ResultBuilder)} method.
   *
   * @param result a {@link ResultBuilder}, that creates the result objects
   * @param <T> the type of the result object
   * @return an instance of a result object, defined by the {@link ResultBuilder}
   * @see Control#execute(ResultBuilder)
   */
  public abstract <T> T compute(ResultBuilder<T, P> result);

  /**
   * Registers the given resource, to make it managed by the control
   *
   * @param resource the resource to manage
   */
  protected void manage(Manageable... resource) {
    this.resources.addAll(Arrays.asList(resource));
  }

  /**
   * Executes the control to run the defined use-case logic. Pass in a {@link ResultBuilder}
   * instance to define the desired result objects. <br>
   * The actual logic is delegated to the {@link ManagingControl#compute(ResultBuilder)} method.
   * This method ensures that all resources are freed, by always calling {@link
   * ManagingControl#close()}
   *
   * @param result a {@link ResultBuilder}, that creates the result objects
   * @param <T> the type of the result object
   * @return an instance of a result object, defined by the {@link ResultBuilder}
   */
  @Override
  public final <T> T execute(ResultBuilder<T, P> result) {
    try {
      return compute(result);
    } finally {
      this.close();
    }
  }

  /** Closes all registered resources */
  @Override
  public void close() {
    resources.forEach(Manageable::close);
  }
}
