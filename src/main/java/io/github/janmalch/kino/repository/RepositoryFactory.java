package io.github.janmalch.kino.repository;

import javax.enterprise.inject.spi.CDI;

public final class RepositoryFactory {

  private RepositoryFactory() {}

  public static <T> Repository<T> createRepository(Class<T> entityType) {
    // return new RepositoryImpl<>(entityType);
    var repo = CDI.current().select(RepositoryImpl.class).get();
    repo.setEntityType(entityType);
    return repo;
  }
}
