package io.github.janmalch.kino.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class JpaTest {

  private static EntityManagerFactory emf;
  static EntityManager em;

  @BeforeAll
  public static void init() {
    emf = Persistence.createEntityManagerFactory("mnf-pu-test");
    em = emf.createEntityManager();
  }

  @AfterAll
  public static void tearDown() {
    em.clear();
    em.close();
    emf.close();
  }
}
