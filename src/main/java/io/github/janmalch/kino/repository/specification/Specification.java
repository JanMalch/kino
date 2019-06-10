package io.github.janmalch.kino.repository.specification;

import javax.persistence.TypedQuery;

/**
 * Interface to create abstract queries
 *
 * @param <T> query result type
 */
public interface Specification<T> {

  /**
   * Transforms this specification in a JPA {@link TypedQuery}
   *
   * @return a typed query for this specification
   */
  TypedQuery<T> toQuery();
}
