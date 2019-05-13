package io.github.janmalch.kino.control.generic;

import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.problem.Problems;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.util.Mapper;
import io.github.janmalch.kino.util.ReflectionMapper;

public class GetEntityControl<P, E> implements Control<P> {

  private final Mapper<E, P> mapper;
  private final long id;
  private final Class<E> entityClass;
  private final Repository<E> repository;

  public GetEntityControl(long id, Class<E> entityClass, Class<P> dtoClass) {
    this(id, entityClass, new ReflectionMapper<>(dtoClass));
  }

  public GetEntityControl(long id, Class<E> entityClass, Mapper<E, P> mapper) {
    this.id = id;
    this.entityClass = entityClass;
    this.repository = RepositoryFactory.createRepository(entityClass);
    this.mapper = mapper;
  }

  @Override
  public <T> T execute(ResultBuilder<T, P> result) {
    var entity =
        Problems.requireEntity(repository.find(id), id, "No such " + entityClass.getSimpleName());
    var dto = mapper.map(entity);
    return result.success(dto);
  }
}
