package io.github.janmalch.kino.repository;

import io.github.janmalch.kino.entity.Movie;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @deprecated Use <code>RepositoryFactory.createRepository(Movie.class);</code> instead
 * @see RepositoryFactory#createRepository(Class)
 */
@Deprecated(forRemoval = true)
public class MovieRepository implements Repository<Movie> {

  private EntityManagerFactory factory = Persistence.createEntityManagerFactory("kino");
  private EntityManager em = factory.createEntityManager();

  @Override
  public Movie find(long id) {
    return em.find(Movie.class, id);
  }

  @Override
  public EntityManager getEntityManager() {
    return em;
  }
}
