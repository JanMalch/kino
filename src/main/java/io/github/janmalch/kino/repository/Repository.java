package io.github.janmalch.kino.repository;

import io.github.janmalch.kino.repository.specification.Specification;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface Repository<DOMAIN> {

  default void add(DOMAIN item) {
    add(Collections.singletonList(item));
  }

  void add(Iterable<DOMAIN> items);

  void update(DOMAIN item);

  /**
   * @param item the entity to delete
   * @return the number of deleted entities, always 1
   */
  int remove(DOMAIN item);

  /**
   * @param specification the specification to delete by
   * @return the number of deleted entities
   */
  int remove(Specification specification);

  List<DOMAIN> query(Specification<DOMAIN> specification);

  default Optional<DOMAIN> queryFirst(Specification<DOMAIN> specification) {
    var result = this.query(specification);
    if (result != null && !result.isEmpty()) {
      return Optional.of(result.get(0));
    } else {
      return Optional.empty();
    }
  }
}
