package io.github.janmalch.kino.util;

import java.util.Map;

/**
 * Generic Mapper interface to map between entity and domain models
 *
 * @param <E> the Entity model
 * @param <D> the Domain model
 */
public interface Mapper<E, D> extends Mapping<E, D> {
  default D mapFromEntity(E entity) {
    throw new UnsupportedOperationException();
  }

  default E mapToEntity(D domain) {
    throw new UnsupportedOperationException();
  }

  @Override
  default D update(E update, D existing, Class<D> targetClass) {
    throw new UnsupportedOperationException();
  }

  @Override
  default D update(E update, D existing, Class<D> targetClass, Map<String, Object> supplies) {
    throw new UnsupportedOperationException();
  }

  @Override
  default D map(E source, Class<D> targetClass) {
    throw new UnsupportedOperationException();
  }

  @Override
  default D map(E source, Class<D> targetClass, Map<String, Object> supplies) {
    return this.mapFromEntity(source);
  }
}
