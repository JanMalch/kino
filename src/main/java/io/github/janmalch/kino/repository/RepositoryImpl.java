package io.github.janmalch.kino.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

final class RepositoryImpl<T> implements Repository<T> {

  private EntityManagerFactory factory = Persistence.createEntityManagerFactory("kino");
  private EntityManager em = factory.createEntityManager();
  private final Class<T> entityType;

  RepositoryImpl(Class<T> entityType) {
    this.entityType = entityType;
  }

  @Override
  public T find(long id) {
    return em.find(entityType, id);
  }

  @Override
  public EntityManager getEntityManager() {
    return em;
  }
}
