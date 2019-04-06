package io.github.janmalch.kino.entity;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestAccount extends JpaTest {

  @Test
  public void testExample() {
    Account pAccount = new Account();
    pAccount.setFirstName("yolo");

    em.getTransaction().begin();
    em.persist(pAccount);
    em.getTransaction().commit();

    List<Account> rAccount =
        (List<Account>) em.createQuery("select a from Account a").getResultList();

    Assertions.assertEquals(1, rAccount.size());
  }
}
