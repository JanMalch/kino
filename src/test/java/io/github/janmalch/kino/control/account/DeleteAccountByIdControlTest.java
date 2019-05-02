package io.github.janmalch.kino.control.account;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.entity.EntityWiper;
import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.entity.Role;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.security.PasswordManager;
import io.github.janmalch.kino.util.either.EitherResultBuilder;
import java.io.IOException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class DeleteAccountByIdControlTest {

  @Test
  void deleteAccountById() throws IOException, ClassNotFoundException {
    EntityWiper ew = new EntityWiper();
    ew.deleteAll();
    Repository<Account> accountRepository = RepositoryFactory.createRepository(Account.class);
    Repository<Reservation> reserverationRepository =
        RepositoryFactory.createRepository(Reservation.class);
    PasswordManager pm = new PasswordManager();
    var salt = pm.generateSalt();
    var existing = new Account();
    var hashedPassword = pm.hashPassword("Password", salt);
    existing.setEmail("existing@example.com");
    existing.setFirstName("Test");
    existing.setLastName("Account");
    existing.setBirthday(LocalDate.now());
    existing.setSalt(salt);
    existing.setRole(Role.CUSTOMER);
    existing.setHashedPassword(hashedPassword);
    accountRepository.add(existing);

    var reservation = new Reservation();
    reservation.setAccount(existing);
    reserverationRepository.add(reservation);
    var getReservation = reserverationRepository.findAll();
    assertEquals(1, getReservation.size());

    var control = new DeleteAccountByIdControl(existing.getId());
    var builder = new EitherResultBuilder<Void>();
    var response = control.execute(builder);
    assertEquals(200, response.getStatus().getStatusCode());

    var getDelReservation = reserverationRepository.findAll();
    assertEquals(0, getDelReservation.size());
  }

  @Test
  void executeFail() {
    var control = new DeleteAccountByIdControl(-1);
    var builder = new EitherResultBuilder<Void>();
    var response = control.execute(builder);
    assertEquals(404, response.getStatus().getStatusCode());
  }
}