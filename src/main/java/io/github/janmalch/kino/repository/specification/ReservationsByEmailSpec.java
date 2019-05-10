package io.github.janmalch.kino.repository.specification;

import io.github.janmalch.kino.entity.Reservation;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class ReservationsByEmailSpec implements Specification<Reservation> {

  private final String email;

  public ReservationsByEmailSpec(String email) {
    this.email = email;
  }

  @Override
  public TypedQuery<Reservation> toQuery() {
    EntityManagerFactory factory = Persistence.createEntityManagerFactory("kino");
    EntityManager em = factory.createEntityManager();
    TypedQuery<Reservation> query =
        em.createQuery(
            "SELECT r FROM Reservation r, Account a where r.account.id = a.id AND a.email = :email",
            Reservation.class);
    query.setParameter("email", email);
    return query;
  }
}
