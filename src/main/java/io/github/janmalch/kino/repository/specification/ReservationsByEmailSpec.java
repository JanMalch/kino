package io.github.janmalch.kino.repository.specification;

import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.repository.Repository;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class ReservationsByEmailSpec implements Specification<Reservation> {

  private final String email;
  private final EntityManager em;

  public ReservationsByEmailSpec(String email, Repository repository) {
    this.email = email;
    this.em = repository.getEntityManager();
  }

  @Override
  public TypedQuery<Reservation> toQuery() {
    TypedQuery<Reservation> query =
        em.createQuery(
            "SELECT r FROM Reservation r, Account a where r.account.id = a.id AND a.email = :email",
            Reservation.class);
    query.setParameter("email", email);
    return query;
  }
}
