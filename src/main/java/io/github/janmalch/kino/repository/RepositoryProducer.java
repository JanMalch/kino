package io.github.janmalch.kino.repository;

import java.lang.reflect.ParameterizedType;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Named;

@Named
@RequestScoped
public class RepositoryProducer {

  // @Inject
  // RepositoryImpl repository;

  @SuppressWarnings("unchecked")
  @Produces
  public <T> Repository<T> produceRepository(InjectionPoint injectionPoint) {
    var type = injectionPoint.getType();
    var parameterizedType = (ParameterizedType) type;
    Class<T> clazz = (Class<T>) parameterizedType.getActualTypeArguments()[0];
    return RepositoryFactory.createRepository(clazz);
    // repository.setEntityType(clazz);
    // return repository;
  }
}
