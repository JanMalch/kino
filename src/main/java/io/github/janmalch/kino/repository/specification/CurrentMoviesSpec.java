package io.github.janmalch.kino.repository.specification;

import io.github.janmalch.kino.entity.Movie;
import io.github.janmalch.kino.repository.Repository;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class CurrentMoviesSpec implements Specification<Movie> {

  private final EntityManager em;

  public CurrentMoviesSpec(Repository repository) {
    this.em = repository.getEntityManager();
  }

  @Override
  public TypedQuery<Movie> toQuery() {
    return em.createQuery("SELECT m FROM Movie m WHERE endDate > current_date()", Movie.class);
  }
}
