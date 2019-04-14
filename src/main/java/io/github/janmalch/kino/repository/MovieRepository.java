package io.github.janmalch.kino.repository;

import io.github.janmalch.kino.entity.Movie;
import io.github.janmalch.kino.repository.specification.Specification;
import java.util.List;
import javax.persistence.*;

public class MovieRepository implements Repository<Movie> {

  private EntityManagerFactory factory = Persistence.createEntityManagerFactory("kino");
  private EntityManager em = factory.createEntityManager();

  @Override
  public void add(Iterable<Movie> items) {
    em.getTransaction().begin();
    items.forEach(em::persist);
    em.getTransaction().commit();
  }

  @Override
  public void update(Movie item) {
    em.getTransaction().begin();
    em.merge(item);
    em.getTransaction().commit();
  }

  @Override
  public int remove(Movie item) {
    em.getTransaction().begin();
    em.remove(item);
    em.getTransaction().commit();
    return 1;
  }

  @Override
  public int remove(Specification specification) {
    em.getTransaction().begin();
    Query query = specification.toQuery();
    int affected = query.executeUpdate();
    em.getTransaction().commit();
    return affected;
  }

  @Override
  public List<Movie> query(Specification<Movie> specification) {
    TypedQuery<Movie> query = specification.toQuery();
    return query.getResultList();
  }
}
