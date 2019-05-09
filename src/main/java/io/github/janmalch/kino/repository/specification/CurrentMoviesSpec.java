package io.github.janmalch.kino.repository.specification;

import io.github.janmalch.kino.entity.Movie;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class CurrentMoviesSpec implements Specification<Movie> {
  @Override
  public TypedQuery<Movie> toQuery() {
    EntityManagerFactory factory = Persistence.createEntityManagerFactory("kino");
    EntityManager em = factory.createEntityManager();
    return em.createQuery("SELECT m FROM Movie m WHERE endDate > current_date()", Movie.class);
  }
}
