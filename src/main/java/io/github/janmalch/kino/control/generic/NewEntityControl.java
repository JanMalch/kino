package io.github.janmalch.kino.control.generic;

import io.github.janmalch.kino.control.ManagingControl;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Identifiable;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.util.Mapper;
import io.github.janmalch.kino.util.ReflectionMapper;

/**
 * Adds a new entity specified by the <code>entityClass</code> in the constructor call. Allows to
 * add either a customer mapper, or map to the given <code>dtoClass</code> via reflection. The
 * mappers are used to map the DTO to an instance of the target <code>entityClass</code>.
 *
 * @param <P> type of the input item
 * @param <E> type of the new entity
 * @see ReflectionMapper
 */
public class NewEntityControl<P, E extends Identifiable> extends ManagingControl<Long> {

  private final Mapper<P, E> mapper;
  private final P dto;
  private final Repository<E> repository;

  public NewEntityControl(P dto, Class<E> entityClass) {
    this(dto, entityClass, new ReflectionMapper<>(entityClass));
  }

  public NewEntityControl(P dto, Class<E> entityClass, Mapper<P, E> mapper) {
    this.dto = dto;
    this.repository = RepositoryFactory.createRepository(entityClass);
    this.mapper = mapper;
  }

  @Override
  public <T> T compute(ResultBuilder<T, Long> result) {
    manage(repository);
    var entity = mapper.map(dto);
    repository.add(entity);
    return result.success(entity.getId());
  }
}
