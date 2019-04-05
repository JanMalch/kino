package io.github.janmalch.kino.util;

public interface Mapper<ENTITY, DOMAIN> {
  default DOMAIN mapFromEntity(ENTITY entity) {
    throw new UnsupportedOperationException();
  }

  default ENTITY mapToEntity(DOMAIN domain) {
    throw new UnsupportedOperationException();
  }
}
