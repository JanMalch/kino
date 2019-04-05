package io.github.janmalch.kino.repository;

import io.github.janmalch.kino.entity.User;
import io.github.janmalch.kino.repository.specification.Specification;
import java.util.List;
import javax.persistence.*;

// TODO: currently uses Entity model
public class UserRepository implements Repository<User> {

  private EntityManagerFactory factory = Persistence.createEntityManagerFactory("kino");
  private EntityManager em = factory.createEntityManager();

  @Override
  public void add(Iterable<User> items) {
    em.getTransaction().begin();
    items.forEach(em::persist);
    em.getTransaction().commit();
  }

  @Override
  public void update(User item) {
    em.getTransaction().begin();
    em.merge(item);
    em.getTransaction().commit();
  }

  @Override
  public int remove(User item) {
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
  public List<User> query(Specification<User> specification) {
    TypedQuery<User> query = specification.toQuery();
    return query.getResultList();
  }
}
