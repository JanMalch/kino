package io.github.janmalch.kino.repository;

import io.github.janmalch.kino.util.functions.VoidConsumer;
import java.util.function.Supplier;
import javax.persistence.EntityManager;

public interface TransactionProvider {
  EntityManager getEntityManager();

  default <T> T inTransaction(Supplier<T> supplier) {
    var em = getEntityManager();
    em.getTransaction().begin();
    var result = supplier.get();
    em.clear();
    em.getTransaction().commit();
    em.close();
    return result;
  }

  default void inTransaction(VoidConsumer supplier) {
    var em = getEntityManager();
    em.getTransaction().begin();
    supplier.execute();
    em.clear();
    em.getTransaction().commit();
    em.close();
  }
}
