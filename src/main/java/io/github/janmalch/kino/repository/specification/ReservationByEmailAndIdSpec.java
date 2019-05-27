package io.github.janmalch.kino.repository.specification;

import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.repository.Repository;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class ReservationByEmailAndIdSpec implements Specification<Reservation> {

  private final String email;
  private final long id;
  private final EntityManager em;

  public ReservationByEmailAndIdSpec(String email, long id, Repository repository) {
    this.email = email;
    this.id = id;
    this.em = repository.getEntityManager();
  }

  @Override
  public TypedQuery<Reservation> toQuery() {
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
