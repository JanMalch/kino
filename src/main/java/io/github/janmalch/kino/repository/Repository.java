package io.github.janmalch.kino.repository;

import io.github.janmalch.kino.repository.specification.Specification;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.Query;

/**
 * Generic Repository interface to define common CRUD methods
 *
 * @param <D> the Domain model
 */
public interface Repository<D> extends TransactionProvider {

  default void add(D item) {
    add(Collections.singletonList(item));
  }

  default void add(Iterable<D> items) {
    var em = getEntityManager();
    inTransaction(() -> items.forEach(em::persist));
  }

  default void update(D item) {
    var em = getEntityManager();
    inTransaction(() -> em.merge(item));
  }

  /**
   * @param item the entity to delete
   * @return the number of deleted entities, always 1
   */
  default int remove(D item) {
    var em = getEntityManager();
    inTransaction(() -> em.remove(em.contains(item) ? item : em.merge(item)));
    return 1;
  }

  /**
   * @param specification the specification to delete by
   * @return the number of deleted entities
   */
  default int remove(Specification specification) {
    return inTransaction(
        () -> {
          Query query = specification.toQuery();
          return query.executeUpdate();
        });
  }

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
    var results = allQuery.getResultList();
    em.close();
    return results;
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
