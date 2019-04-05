package io.github.janmalch.kino.repository.specification;

import io.github.janmalch.kino.entity.Account;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class UserByEmailSpec implements Specification<Account> {

  private final String email;

  public UserByEmailSpec(String email) {
    this.email = email;
  }

  @Override
  public TypedQuery<Account> toQuery() {
    EntityManagerFactory factory = Persistence.createEntityManagerFactory("kino");
    EntityManager em = factory.createEntityManager();
    TypedQuery<Account> query =
        em.createQuery("SELECT u FROM Account u WHERE email = :email", Account.class);
    query.setParameter("email", email);
    query.setMaxResults(1);
    return query;
  }
}
