package io.github.janmalch.kino.entity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface Repository<DOMAIN> {

  default void add(DOMAIN item) {
    add(Collections.singletonList(item));
  }

  void add(Iterable<DOMAIN> items);

  void update(DOMAIN item);

  void remove(DOMAIN item);

  void remove(Specification specification);

  List<DOMAIN> query(Specification specification);

  default Optional<DOMAIN> queryFirst(Specification specification) {
    var result = this.query(specification);
    if (result != null && result.size() > 0) {
      return Optional.of(result.get(0));
    } else {
      return Optional.empty();
    }
  }
}
