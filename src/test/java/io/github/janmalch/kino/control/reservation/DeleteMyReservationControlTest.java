package io.github.janmalch.kino.control.reservation;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.janmalch.kino.api.model.ReservationInfoDto;
import io.github.janmalch.kino.api.model.account.SafeAccountInfoDto;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.entity.EntityWiper;
import io.github.janmalch.kino.util.either.EitherResultBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeleteMyReservationControlTest {

  private ReservationTestUtil util;

  @BeforeEach
  public void setUp() {
    util = new ReservationTestUtil();
  }

  @AfterEach
  public void tearDown() {
    var ew = new EntityWiper();
    ew.wipeDB();
  }

  @Test
  public void deleteMyAccount() {
    String myAccount = "my@account.de";
    var presentation = util.provideReservationSetup("first", 10, myAccount);
    var reservation = util.provideNewReservation(myAccount, presentation.getId());

    var accountInfoDto = new SafeAccountInfoDto();
    accountInfoDto.setEmail(myAccount);
    accountInfoDto.setId(reservation.getId());

    var account = new Account();
    account.setEmail(myAccount);

    var newReservationInfoDto = new ReservationInfoDto();
    newReservationInfoDto.setAccount(accountInfoDto);
    newReservationInfoDto.setId(reservation.getId());

    var control = new DeleteMyReservation(newReservationInfoDto);
    var result = control.execute(new EitherResultBuilder<>());

    assertTrue(result.isSuccess());
  }

  @Test
  public void deleteMyAccountNotSameAccount() {
    String myAccount = "my@account.de";
    var presentation = util.provideReservationSetup("first", 10, myAccount);
    var reservation = util.provideNewReservation(myAccount, presentation.getId());

    var accountInfoDto = new SafeAccountInfoDto();
    accountInfoDto.setEmail("other@account.de");
    accountInfoDto.setId(1);

    var account = new Account();
    account.setEmail("other@account.de");

    var newReservationInfoDto = new ReservationInfoDto();
    newReservationInfoDto.setAccount(accountInfoDto);
    newReservationInfoDto.setId(reservation.getId());

    var control = new DeleteMyReservation(newReservationInfoDto);
    var result = control.execute(new EitherResultBuilder<>());

    assertTrue(result.isFailure());
  }
}
