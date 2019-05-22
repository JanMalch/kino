package io.github.janmalch.kino.repository;

import io.github.janmalch.kino.repository.specification.Specification;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

@RequestScoped
public class RepositoryImpl implements Repository {

  @PersistenceContext(unitName = "kino")
  private EntityManager em;

  private Class<?> entityType;

  @SuppressWarnings("unchecked")
  @Override
  @Transactional
  public void add(Iterable items) {
    items.forEach(em::persist);
  }

  @Override
  @Transactional
  public void update(Object item) {
    em.merge(item);
  }

  @Override
  @Transactional
  public int remove(Object item) {
    em.remove(em.contains(item) ? item : em.merge(item));
    return 1;
  }

  @Override
  @Transactional
  public int remove(Specification specification) {
    Query query = specification.toQuery();
    return query.executeUpdate();
  }

  @Override
  public EntityManager getEntityManager() {
    return em;
  }

  void setEntityType(Class<?> entityType) {
    this.entityType = entityType;
  }

  @Override
  public Class<?> getEntityType() {
    return entityType;
  }

  @Override
  public String toString() {
    return "RepositoryImpl{" + "entityType=" + entityType + ", em=" + em + '}';
  }
}
