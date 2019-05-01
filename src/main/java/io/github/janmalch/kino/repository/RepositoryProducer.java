package io.github.janmalch.kino.repository;

import java.lang.reflect.ParameterizedType;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Named;

@Named
@ApplicationScoped
public class RepositoryProducer {

  @SuppressWarnings("unchecked")
  @Produces
  public <T> Repository<T> produceRepository(InjectionPoint injectionPoint) {
    var type = injectionPoint.getType();
    var parameterizedType = (ParameterizedType) type;
    Class<T> clazz = (Class<T>) parameterizedType.getActualTypeArguments()[0];
    return RepositoryFactory.createRepository(clazz);
  }
}
