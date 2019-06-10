package io.github.janmalch.kino.repository;

import io.github.janmalch.kino.repository.specification.Specification;
import io.github.janmalch.kino.util.Manageable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.Query;

/**
 * Generic Repository interface to define common CRUD methods
 *
 * @param <D> the Domain model
 */
public interface Repository<D> extends TransactionProvider, Manageable {

  /**
   * Adds a single item.
   *
   * @param item the item to add
   */
  default void add(D item) {
    add(Collections.singletonList(item));
  }

  /**
   * Adds multiple items in a row
   *
   * @param items the items to add
   */
  default void add(Iterable<D> items) {
    var em = getEntityManager();
    inTransaction(() -> items.forEach(em::persist));
  }

  /**
   * Updates a single item
   *
   * @param item the partial update
   */
  default void update(D item) {
    var em = getEntityManager();
    inTransaction(() -> em.merge(item));
  }

  /**
   * Removes the given item from the database
   *
   * @param item the entity to delete
   * @return the number of deleted entities, always 1
   */
  default int remove(D item) {
    var em = getEntityManager();
    inTransaction(() -> em.remove(em.contains(item) ? item : em.merge(item)));
    return 1;
  }

  /**
   * Removes items by executing the specification
   *
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

  /**
   * Finds the item for the given id
   *
   * @param id the queried id
   * @return the item for the given ID. <code>null</code> if no item exists
   */
  default D find(long id) {
    var entityType = getEntityType();
    var em = getEntityManager();
    return em.find(entityType, id);
  }

  /**
   * Finds all items that are managed by this repository.
   *
   * @return a list of all items
   */
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

  /**
   * Executes the given specification and returns its result list
   *
   * @param specification the specification to run a query with
   * @return a list of all matching items
   */
  default List<D> query(Specification<D> specification) {
    var query = specification.toQuery();
    return query.getResultList();
  }

  /**
   * Delegates the execution to {@link Repository#query(Specification)} and safely wraps the first
   * item in the list in an Optional. If the list is empty {@link Optional#empty()} will be
   * returned.
   *
   * @param specification the specification to run a query with
   * @return an {@link Optional} for the first result of the query
   */
  default Optional<D> queryFirst(Specification<D> specification) {
    var result = this.query(specification);
    if (result != null && !result.isEmpty()) {
      return Optional.of(result.get(0));
    } else {
      return Optional.empty();
    }
  }

  /**
   * Returns the entity type that this repository is managing.
   *
   * @return the managed entity type
   */
  default Class<D> getEntityType() {
    throw new UnsupportedOperationException("Entity type not provided by this repository");
  }

  /** Closes all resources that this repository might use. */
  @Override
  default void close() {
    getEntityManager().close();
  }
}
