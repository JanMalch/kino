package io.github.janmalch.kino.repository.specification;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.entity.EntityWiper;
import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReservationByEmailAndIdSpecTest {

  private Repository<Account> accountRepository = RepositoryFactory.createRepository(Account.class);
  private Repository<Reservation> reservationRepository =
      RepositoryFactory.createRepository(Reservation.class);
  private Reservation reservation = new Reservation();
  private String mail = "test@spec.com";

  @BeforeEach
  public void setUp() {
    var account = new Account();
    account.setEmail(mail);
    reservation.setAccount(account);

    accountRepository.add(account);
    reservationRepository.add(reservation);

    reservationRepository.getEntityManager().clear();
    accountRepository.getEntityManager().clear();
  }

  @AfterEach
  public void tearDown() {
    var ew = new EntityWiper();
    ew.wipeDB();
  }

  @Test
  public void testQuery() {
    var query = new ReservationByEmailAndIdSpec(mail, reservation.getId());
    var result = reservationRepository.queryFirst(query);

    assertTrue(result.isPresent());
    assertEquals(mail, result.get().getAccount().getEmail());
  }
}
