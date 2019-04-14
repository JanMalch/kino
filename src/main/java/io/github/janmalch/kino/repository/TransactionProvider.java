package io.github.janmalch.kino.repository;

import java.util.function.Supplier;
import javax.persistence.EntityManager;

public interface TransactionProvider {
  EntityManager getEntityManager();

  default <T> T inTransaction(Supplier<T> supplier) {
    var em = getEntityManager();
    em.getTransaction().begin();
    var result = supplier.get();
    em.getTransaction().commit();
    return result;
  }

  default void inTransaction(VoidConsumer supplier) {
    var em = getEntityManager();
    em.getTransaction().begin();
    supplier.execute();
    em.getTransaction().commit();
  }
}
