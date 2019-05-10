package io.github.janmalch.kino.control.generic;

import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Identifiable;
import io.github.janmalch.kino.problem.Problems;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.util.Mapping;
import io.github.janmalch.kino.util.ReflectionMapper;

public class UpdateEntityControl<P, E extends Identifiable> implements Control<Void> {

  private final Mapping<P, E> mapper;
  private final long id;
  private final P dto;
  private final Class<E> entityClass;
  private final Repository<E> repository;

  public UpdateEntityControl(long id, P dto, Class<E> entityClass) {
    this(id, dto, entityClass, new ReflectionMapper<>(entityClass));
  }

  public UpdateEntityControl(long id, P dto, Class<E> entityClass, Mapping<P, E> mapper) {
    this.id = id;
    this.dto = dto;
    this.entityClass = entityClass;
    this.repository = RepositoryFactory.createRepository(entityClass);
    this.mapper = mapper;
  }

  @Override
  public <T> T execute(ResultBuilder<T, Void> result) {
    var entity =
        Problems.requireEntity(repository.find(id), id, "No such " + entityClass.getSimpleName());
    var update = mapper.update(dto, entity);
    update.setId(id);
    repository.update(update);
    return result.success(entityClass.getSimpleName() + " successfully updated");
  }
}
