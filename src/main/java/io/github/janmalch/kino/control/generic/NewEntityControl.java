package io.github.janmalch.kino.control.generic;

import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Identifiable;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.util.Mapping;
import io.github.janmalch.kino.util.ReflectionMapper;

public class NewEntityControl<P, E extends Identifiable> implements Control<Long> {

  private final Mapping<P, E> mapper;
  private final P dto;
  private final Repository<E> repository;

  public NewEntityControl(P dto, Class<E> entityClass) {
    this(dto, entityClass, new ReflectionMapper<>(entityClass));
  }

  public NewEntityControl(P dto, Class<E> entityClass, Mapping<P, E> mapper) {
    this.dto = dto;
    this.repository = RepositoryFactory.createRepository(entityClass);
    this.mapper = mapper;
  }

  @Override
  public <T> T execute(ResultBuilder<T, Long> result) {
    var entity = mapper.map(dto);
    repository.add(entity);
    return result.success(entity.getId());
  }
}
