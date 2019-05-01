package io.github.janmalch.kino.api;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@ApplicationScoped
public class LoggerProducer {

  @Produces
  public Logger produceLogger(InjectionPoint injectionPoint) {
    return LoggerFactory.getLogger(injectionPoint.getBean().getBeanClass());
  }
}
