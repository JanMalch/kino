package io.github.janmalch.kino.control.generic;

import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.problem.Problems;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;

public class DeleteEntityControl<E> implements Control<Void> {

  private final long id;
  private final Class<E> entityClass;
  private final Repository<E> repository;

  public DeleteEntityControl(long id, Class<E> entityClass) {
    this.id = id;
    this.entityClass = entityClass;
    this.repository = RepositoryFactory.createRepository(entityClass);
  }

  @Override
  public <T> T execute(ResultBuilder<T, Void> result) {
    var entity =
        Problems.requireEntity(repository.find(id), id, "No such " + entityClass.getSimpleName());
    repository.remove(entity);
    return result.success(null, entityClass.getSimpleName() + " has been removed");
  }
}
