package io.github.janmalch.kino.control.generic;

import io.github.janmalch.kino.control.ManagingControl;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.util.Mapper;
import io.github.janmalch.kino.util.ReflectionMapper;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Gets all entities specified by the <code>entityClass</code> in the constructor call. Allows to
 * add either a customer mapper, or map to the given <code>dtoClass</code> via reflection.
 *
 * @param <P> type of resulting output items
 * @param <E> type of the queried entities
 * @see ReflectionMapper
 */
public class GetEntitiesControl<P, E> extends ManagingControl<List<P>> {

  private final Mapper<E, P> mapper;
  private final Repository<E> repository;

  public GetEntitiesControl(Class<E> entityClass, Class<P> dtoClass) {
    this(entityClass, new ReflectionMapper<>(dtoClass));
  }

  public GetEntitiesControl(Class<E> entityClass, Mapper<E, P> mapper) {
    this.mapper = mapper;
    this.repository = RepositoryFactory.createRepository(entityClass);
  }

  @Override
  public <T> T compute(ResultBuilder<T, List<P>> result) {
    manage(repository);
    var entities = repository.findAll();
    var dtos = entities.stream().map(mapper::map).collect(Collectors.toList());
    return result.success(dtos);
  }
}
