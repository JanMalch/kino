package io.github.janmalch.kino.repository;

import io.github.janmalch.kino.repository.specification.Specification;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;

/**
 * Generic Repository interface to define common CRUD methods
 *
 * @param <D> the Domain model
 */
public interface Repository<D> {

  default void add(D item) {
    add(Collections.singletonList(item));
  }

  void add(Iterable<D> items);

  void update(D item);

  /**
   * @param item the entity to delete
   * @return the number of deleted entities, always 1
   */
  int remove(D item);

  /**
   * @param specification the specification to delete by
   * @return the number of deleted entities
   */
  int remove(Specification specification);

  EntityManager getEntityManager();

  default D find(long id) {
    var entityType = getEntityType();
    var em = getEntityManager();
    return em.find(entityType, id);
  }

  default List<D> findAll() {
    var entityType = getEntityType();
    var em = getEntityManager();

    var cb = em.getCriteriaBuilder();
    var cq = cb.createQuery(entityType);
    var rootEntry = cq.from(entityType);

    var all = cq.select(rootEntry);
    var allQuery = em.createQuery(all);
    return allQuery.getResultList();
  }

  default List<D> query(Specification<D> specification) {
    var query = specification.toQuery();
    return query.getResultList();
  }

  default Optional<D> queryFirst(Specification<D> specification) {
    var result = this.query(specification);
    if (result != null && !result.isEmpty()) {
      return Optional.of(result.get(0));
    } else {
      return Optional.empty();
    }
  }

  default Class<D> getEntityType() {
    throw new UnsupportedOperationException("Entity type not provided by this repository");
  }
}
