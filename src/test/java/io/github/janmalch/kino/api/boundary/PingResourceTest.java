package io.github.janmalch.kino.api.boundary;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.ws.rs.core.Application;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

class PingResourceTest extends JerseyTest {

  @Mock private Logger logger;

  @InjectMocks private PingResource resource;

  @Override
  protected Application configure() {
    MockitoAnnotations.initMocks(this);
    return new ResourceConfig()
        .register(PingResource.class)
        .register(
            new AbstractBinder() {
              @Override
              protected void configure() {
                bind(logger).to(Logger.class);
              }
            });
  }

  @Test
  void ping() {
    var dto = resource.ping();
    assertNotNull(dto.getResponse());
  }
}
