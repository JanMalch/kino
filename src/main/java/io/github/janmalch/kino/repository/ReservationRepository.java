package io.github.janmalch.kino.repository;

import io.github.janmalch.kino.entity.Reservation;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ReservationRepository implements Repository<Reservation> {

  private EntityManagerFactory factory = Persistence.createEntityManagerFactory("kino");
  private EntityManager em = factory.createEntityManager();

  @Override
  public Reservation find(long id) {
    return em.find(Reservation.class, id);
  }

  @Override
  public EntityManager getEntityManager() {
    return em;
  }
}
