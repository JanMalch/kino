package io.github.janmalch.kino.repository;

import io.github.janmalch.kino.entity.Seat;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SeatRepository implements Repository<Seat> {

  private EntityManagerFactory factory = Persistence.createEntityManagerFactory("kino");
  private EntityManager em = factory.createEntityManager();

  @Override
  public Seat find(long id) {
    return em.find(Seat.class, id);
  }

  @Override
  public EntityManager getEntityManager() {
    return em;
  }
}
