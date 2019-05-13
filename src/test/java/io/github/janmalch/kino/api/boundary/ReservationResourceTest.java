package io.github.janmalch.kino.api.boundary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.janmalch.kino.api.model.ReservationInfoDto;
import io.github.janmalch.kino.control.reservation.ReservationTestUtil;
import io.github.janmalch.kino.entity.EntityWiper;
import io.github.janmalch.kino.entity.Presentation;
import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.security.JwtTokenFactory;
import io.github.janmalch.kino.security.Token;
import io.github.janmalch.kino.security.TokenSecurityContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.core.SecurityContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReservationResourceTest {

  private ReservationTestUtil util;
  private Presentation presentation;
  private SecurityContext securityContext;
  private String myAccount = "my@account.com";

  @BeforeEach
  public void setUp() {
    util = new ReservationTestUtil();
    presentation = util.provideReservationSetup("first", 10, myAccount);

    JwtTokenFactory factory = new JwtTokenFactory();
    Token token = factory.generateToken(myAccount);
    securityContext = new TokenSecurityContext(token);
  }

  @AfterEach
  public void tearDown() {
    var ew = new EntityWiper();
    ew.wipeDB();
  }

  @Test
  public void testNewReservation() {
    var resource = new ReservationResource();

    var reservationDto = util.provideNewReservationDto(presentation.getId());

    var response = resource.newReservation(securityContext, reservationDto);
    assertEquals(200, response.getStatus());
    var reservationId = (long) response.getEntity();
    var reservation = RepositoryFactory.createRepository(Reservation.class).find(reservationId);
    assertEquals(reservationDto.getSeatIds().size(), reservation.getSeats().size());
    assertEquals(reservationDto.getPresentationId(), reservation.getPresentation().getId());
    assertEquals(myAccount, reservation.getAccount().getEmail());
  }

  @Test
  public void testGetMyReservations() {
    var resource = new ReservationResource();
    var cinemaHallNames = Arrays.asList("first", "second", "third");
    var numberOfSeats = 10;
    var accountnames = Arrays.asList("my@account.com", "my@account.com", "her@account.com");
    var presentations = new ArrayList<Presentation>();
    var reservations = new ArrayList<Reservation>();

    for (int i = 0; i < cinemaHallNames.size(); i++) {
      presentations.add(
          util.provideReservationSetup(cinemaHallNames.get(i), numberOfSeats, accountnames.get(i)));
      reservations.add(
          util.provideNewReservation(accountnames.get(i), presentations.get(i).getId()));
    }

    var response = resource.getMyReservations(securityContext);
    assertEquals(200, response.getStatus());
    var reservationInfoDtos = (List<ReservationInfoDto>) response.getEntity();
    assertEquals(2, reservationInfoDtos.size());
  }

  @Test
  public void testGetReservationById() {
    var reservation = util.provideNewReservation(myAccount, presentation.getId());

    var resource = new ReservationResource();
    var response = resource.getReservationById(reservation.getId(), securityContext);
    assertEquals(200, response.getStatus());
  }

  @Test
  public void testGetAllReservations() {
    var resource = new ReservationResource();
    var cinemaHallNames = Arrays.asList("first", "second", "third");
    var numberOfSeats = 10;
    var accountnames = Arrays.asList("my@account.com", "his@account.com", "her@account.com");
    var presentations = new ArrayList<Presentation>();
    var reservations = new ArrayList<Reservation>();

    for (int i = 0; i < cinemaHallNames.size(); i++) {
      presentations.add(
          util.provideReservationSetup(cinemaHallNames.get(i), numberOfSeats, accountnames.get(i)));
      reservations.add(
          util.provideNewReservation(accountnames.get(i), presentations.get(i).getId()));
    }

    var response = resource.getAllReservations();
    var reservationDtos = (List<ReservationInfoDto>) response.getEntity();

    assertEquals(200, response.getStatus());
    assertEquals(reservations.size(), reservationDtos.size());
  }

  @Test
  public void testUpdateReservationById() {
    var existingReservation = util.provideNewReservation(myAccount, presentation.getId());
    var updateReservationDto =
        util.getReservationDto(existingReservation, presentation.getCinemaHall().getSeats());

    var resource = new ReservationResource();
    var response =
        resource.updateReservationById(
            existingReservation.getId(), securityContext, updateReservationDto);
    assertEquals(200, response.getStatus());
  }

  @Test
  public void testDeleteReservationById() {
    var existingReservation = util.provideNewReservation(myAccount, presentation.getId());

    var resource = new ReservationResource();
    var response = resource.deleteReservationById(existingReservation.getId(), securityContext);
    assertEquals(200, response.getStatus());
  }
}
