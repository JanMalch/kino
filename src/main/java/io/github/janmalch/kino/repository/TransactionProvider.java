package io.github.janmalch.kino.repository;

import io.github.janmalch.kino.util.functions.VoidConsumer;
import java.util.function.Supplier;
import javax.persistence.EntityManager;

/**
 * Interfaces to separate transaction handling. Provides two methods to wrap any lambda in a
 * transaction and return its results.
 */
public interface TransactionProvider {

  /**
   * Returns an EntityManager instance, to get a transaction from
   *
   * @return an EntityManager instance
   */
  EntityManager getEntityManager();

  /**
   * Wraps the given Supplier in a transaction and returns its result.
   *
   * @param supplier a function to run within a transaction
   * @param <T> the result type of the supplier function
   * @return the result of the supplier function
   */
  default <T> T inTransaction(Supplier<T> supplier) {
    var em = getEntityManager();
    em.getTransaction().begin();
    var result = supplier.get();
    em.getTransaction().commit();
    return result;
  }

  /**
   * Wraps the given VoidConsumer in a transaction and executes it.
   *
   * @param supplier a function to run within a transaction
   */
  default void inTransaction(VoidConsumer supplier) {
    var em = getEntityManager();
    em.getTransaction().begin();
    supplier.execute();
    em.getTransaction().commit();
  }
}
