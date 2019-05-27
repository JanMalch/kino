package io.github.janmalch.kino.repository.specification;

import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.repository.Repository;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class AccountByEmailSpec implements Specification<Account> {

  private final String email;
  private final EntityManager em;

  public AccountByEmailSpec(String email, Repository<Account> repository) {
    this.email = email;
    this.em = repository.getEntityManager();
  }

  @Override
  public TypedQuery<Account> toQuery() {
    TypedQuery<Account> query =
        em.createQuery("SELECT u FROM Account u WHERE email = :email", Account.class);
    query.setParameter("email", email);
    query.setMaxResults(1);
    return query;
  }
}
