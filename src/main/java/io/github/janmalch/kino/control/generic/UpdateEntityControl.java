package io.github.janmalch.kino.control.generic;

import io.github.janmalch.kino.control.ManagingControl;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Identifiable;
import io.github.janmalch.kino.problem.Problems;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.util.Mapper;
import io.github.janmalch.kino.util.ReflectionMapper;

/**
 * Updates an existing entity specified by the ID and <code>entityClass</code> in the constructor
 * call. Allows to add either a customer mapper, or map to the given <code>dtoClass</code> via
 * reflection. The mappers are used to map the DTO to an instance of the target <code>entityClass
 * </code>.
 *
 * @param <P> type of the input item
 * @param <E> type of the new entity
 * @see ReflectionMapper
 */
public class UpdateEntityControl<P, E extends Identifiable> extends ManagingControl<Void> {

  private final Mapper<P, E> mapper;
  private final long id;
  private final P dto;
  private final Class<E> entityClass;
  private final Repository<E> repository;

  public UpdateEntityControl(long id, P dto, Class<E> entityClass) {
    this(id, dto, entityClass, new ReflectionMapper<>(entityClass));
  }

  public UpdateEntityControl(long id, P dto, Class<E> entityClass, Mapper<P, E> mapper) {
    this.id = id;
    this.dto = dto;
    this.entityClass = entityClass;
    this.repository = RepositoryFactory.createRepository(entityClass);
    this.mapper = mapper;
  }

  @Override
  public <T> T compute(ResultBuilder<T, Void> result) {
    manage(repository);
    var entity =
        Problems.requireEntity(repository.find(id), id, "No such " + entityClass.getSimpleName());
    var update = mapper.update(dto, entity);
    update.setId(id);
    repository.update(update);
    return result.success(entityClass.getSimpleName() + " successfully updated");
  }
}
