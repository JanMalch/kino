package io.github.janmalch.kino.repository;

public final class RepositoryFactory {

  public static <T> Repository<T> createRepository(Class<T> entityType) {
    return new RepositoryImpl<>(entityType);
  }
}
