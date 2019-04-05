package io.github.janmalch.kino.util;

/**
 * Generic Mapper interface to map between entity and domain models
 *
 * @param <E> the Entity model
 * @param <D> the Domain model
 */
public interface Mapper<E, D> {
  default D mapFromEntity(E entity) {
    throw new UnsupportedOperationException();
  }

  default E mapToEntity(D domain) {
    throw new UnsupportedOperationException();
  }
}
