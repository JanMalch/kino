package io.github.janmalch.kino.control.generic;

import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.util.Mapping;
import io.github.janmalch.kino.util.ReflectionMapper;
import java.util.List;
import java.util.stream.Collectors;

public class GetEntitiesControl<P, E> implements Control<List<P>> {

  private final Mapping<E, P> mapper;
  private final Class<P> dtoClass;
  private final Repository<E> repository;

  public GetEntitiesControl(Class<E> entityClass, Class<P> dtoClass) {
    this(entityClass, dtoClass, new ReflectionMapper<>());
  }

  public GetEntitiesControl(Class<E> entityClass, Class<P> dtoClass, Mapping<E, P> mapper) {
    this.mapper = mapper;
    this.dtoClass = dtoClass;
    this.repository = RepositoryFactory.createRepository(entityClass);
  }

  @Override
  public <T> T execute(ResultBuilder<T, List<P>> result) {
    var entities = repository.findAll();
    var dtos =
        entities.stream().map(entity -> mapper.map(entity, dtoClass)).collect(Collectors.toList());
    return result.success(dtos);
  }
}
