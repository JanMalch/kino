package io.github.janmalch.kino.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import io.github.janmalch.kino.repository.specification.Specification;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.junit.jupiter.api.Test;

class RepositoryTest {

  @Test
  void add() {
    var repository = new TestRepository();
    repository.add(new Object());
    assertTrue(repository.hasAdded);
  }

  @Test
  void queryFirstEmpty() {
    var repository = new TestRepository();
    var result = repository.queryFirst(null);
    assertTrue(result.isEmpty());
  }

  @Test
  void queryFirstPresent() {
    var repository = new TestRepository();
    var result = repository.queryFirst(new TestSpecification());
    assertTrue(result.isPresent());
  }

  @Test
  void find() {
    var repository = new TestRepository();
    try {
      repository.find(1);
      fail("default implementation should throw UnsupportedOperationException");
    } catch (UnsupportedOperationException e) {
      // this Exception is expected
    }
  }

  private static class TestRepository implements Repository<Object> {

    boolean hasAdded = false;

    @Override
    public void add(Iterable<Object> items) {
      hasAdded = true;
    }

    @Override
    public void update(Object item) {}

    @Override
    public int remove(Object item) {
      return 0;
    }

    @Override
    public int remove(Specification specification) {
      return 0;
    }

    @Override
    public List<Object> query(Specification<Object> specification) {
      // dumb way to have both branches
      return specification == null ? List.of() : List.of(new Object());
    }

    @Override
    public EntityManager getEntityManager() {
      return null;
    }
  }

  private static class TestSpecification implements Specification<Object> {

    @Override
    public TypedQuery<Object> toQuery() {
      return null;
    }
  }
}
