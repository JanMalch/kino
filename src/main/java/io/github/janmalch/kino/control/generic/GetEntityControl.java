package io.github.janmalch.kino.control.generic;

import io.github.janmalch.kino.control.ManagingControl;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.problem.Problems;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.util.Mapper;
import io.github.janmalch.kino.util.ReflectionMapper;

/**
 * Get the entity specified by the given ID and <code>entityClass</code> in the constructor call.
 * Allows to add either a customer mapper, or map to the given <code>dtoClass</code> via reflection.
 *
 * @param <P> type of resulting output items
 * @param <E> type of the queried entities
 * @see ReflectionMapper
 */
public class GetEntityControl<P, E> extends ManagingControl<P> {

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
  public <T> T compute(ResultBuilder<T, P> result) {
    manage(repository);
    var entity =
        Problems.requireEntity(repository.find(id), id, "No such " + entityClass.getSimpleName());
    var dto = mapper.map(entity);
    return result.success(dto);
  }
}
