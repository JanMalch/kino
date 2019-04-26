package io.github.janmalch.kino.repository;

import io.github.janmalch.kino.entity.Account;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

// TODO: currently uses Entity model
/**
 * @deprecated Use <code>RepositoryFactory.createRepository(Account.class);</code> instead
 * @see RepositoryFactory#createRepository(Class)
 */
@Deprecated(forRemoval = true)
public class UserRepository implements Repository<Account> {

  private EntityManagerFactory factory = Persistence.createEntityManagerFactory("kino");
  private EntityManager em = factory.createEntityManager();

  @Override
  public Account find(long id) {
    return em.find(Account.class, id);
  }

  @Override
  public EntityManager getEntityManager() {
    return em;
  }
}
