package io.github.janmalch.kino.repository.specification;

import io.github.janmalch.kino.entity.Reservation;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class ReservationByEmailAndIdSpec implements Specification<Reservation> {

  private final String email;
  private final long id;

  public ReservationByEmailAndIdSpec(String email, long id) {
    this.email = email;
    this.id = id;
  }

  @Override
  public TypedQuery<Reservation> toQuery() {
    EntityManagerFactory factory = Persistence.createEntityManagerFactory("kino");
    EntityManager em = factory.createEntityManager();
    TypedQuery<Reservation> query =
        em.createQuery(
            "SELECT r FROM Reservation r, Account a where r.account.id = a.id AND a.email = :email AND r.id = :id",
            Reservation.class);
    query.setParameter("email", email);
    query.setParameter("id", id);
    query.setMaxResults(1);
    return query;
  }
}
