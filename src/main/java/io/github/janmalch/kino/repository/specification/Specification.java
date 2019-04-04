package io.github.janmalch.kino.repository.specification;

import javax.persistence.TypedQuery;

public interface Specification<T> {

  TypedQuery<T> toQuery();
}
