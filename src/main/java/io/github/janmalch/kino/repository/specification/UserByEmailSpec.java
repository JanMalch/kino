package io.github.janmalch.kino.repository.specification;

import io.github.janmalch.kino.entity.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class UserByEmailSpec implements Specification<User> {

  private final String email;

  public UserByEmailSpec(String email) {
    this.email = email;
  }

  @Override
  public TypedQuery<User> toQuery() {
    EntityManagerFactory factory = Persistence.createEntityManagerFactory("kino");
    EntityManager em = factory.createEntityManager();
    TypedQuery<User> query =
        em.createQuery("SELECT u FROM User u WHERE email = :email", User.class);
    query.setParameter("email", email);
    query.setMaxResults(1);
    return query;
  }
}
