package io.github.janmalch.kino.api;

import io.github.janmalch.kino.repository.RepositoryProducer;
import io.github.janmalch.kino.security.AuthorizationFilter;
import io.swagger.jaxrs.config.BeanConfig;
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("api")
public class WebApplication extends ResourceConfig {

  public WebApplication() {
    BeanConfig beanConfig = new BeanConfig();
    beanConfig.setTitle("Kino API");
    beanConfig.setVersion("1.0.0");
    beanConfig.setSchemes(new String[] {"http"});
    beanConfig.setHost("localhost:8080");
    beanConfig.setBasePath("/kino/api");
    beanConfig.setResourcePackage("io.github.janmalch.kino.api.boundary");
    beanConfig.setScan(true);

    packages("io.github.janmalch.kino.api.boundary");
    register(AuthorizationFilter.class);
    register(APIExceptionMapper.class);
    register(RepositoryProducer.class);
    register(LoggerProducer.class);
    register(ValidationExceptionMapper.class);
    register(new CORSFilter());

    register(io.swagger.jaxrs.listing.ApiListingResource.class);
    register(io.swagger.jaxrs.listing.SwaggerSerializers.class);
  }
}
