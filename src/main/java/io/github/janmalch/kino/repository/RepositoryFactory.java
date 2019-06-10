package io.github.janmalch.kino.repository;

/**
 * A factory class that provides a single method to generate new repositories.
 *
 * @see RepositoryFactory#createRepository(Class)
 */
public final class RepositoryFactory {

  private RepositoryFactory() {}

  /**
   * Creates a new repository that manages entities, specified by the entity class.
   *
   * @param entityType the entity type to create a repository for
   * @param <T> the entity type
   * @return a new repository instance
   */
  public static <T> Repository<T> createRepository(Class<T> entityType) {
    return new RepositoryImpl<>(entityType);
  }
}
