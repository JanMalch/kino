package io.github.janmalch.kino.util;

/**
 * Generic Mapper interface to map between entity and domain models
 *
 * @param <E> the Entity model
 * @param <D> the Domain model
 */
@Deprecated(forRemoval = true)
public interface Mapper<E, D> extends Mapping<E, D> {
  @Deprecated
  default D mapFromEntity(E entity) {
    throw new UnsupportedOperationException();
  }

  @Deprecated
  default E mapToEntity(D domain) {
    throw new UnsupportedOperationException();
  }

  @Override
  default D update(E update, D existing) {
    throw new UnsupportedOperationException();
  }

  @Override
  default D map(E source) {
    throw new UnsupportedOperationException();
  }
}
