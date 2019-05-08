package io.github.janmalch.kino.repository.specification;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReservationsByEmailSpecTest {

  private Repository<Account> accountRepository = RepositoryFactory.createRepository(Account.class);
  private Account account = new Account();
  private String mail = "test@spec.com";

  @BeforeEach
  public void setUp() {
    account.setEmail(mail);
    accountRepository.add(account);
    accountRepository.getEntityManager().clear();
  }

  @Test
  public void testQuery() {
    var reservationRepository = RepositoryFactory.createRepository(Reservation.class);
    var reservation = new Reservation();
    reservation.setAccount(account);
    reservationRepository.add(reservation);

    var query = new ReservationsByEmailSpec(mail);
    var result = reservationRepository.query(query);

    assertEquals(1, result.size());
    assertEquals(mail, result.get(0).getAccount().getEmail());
  }

  @Test
  public void testMultipleResults() {
    var reservationRepository = RepositoryFactory.createRepository(Reservation.class);

    var number = 5;

    for (int i = 0; i < number; i++) {
      var reservation = new Reservation();
      reservation.setAccount(account);
      reservationRepository.add(reservation);
    }

    reservationRepository.getEntityManager().clear();

    var query = new ReservationsByEmailSpec(mail);
    var result = reservationRepository.query(query);

    assertEquals(number, result.size());
  }
}
