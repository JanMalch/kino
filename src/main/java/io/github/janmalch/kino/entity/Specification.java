package io.github.janmalch.kino.entity;

import javax.persistence.Query;

public interface Specification {

  Query toQuery();
}
