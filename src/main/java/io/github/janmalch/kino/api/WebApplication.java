package io.github.janmalch.kino.api;

import io.github.janmalch.kino.api.boundary.AccountResource;
import io.github.janmalch.kino.api.boundary.AuthResource;
import io.github.janmalch.kino.api.boundary.PingResource;
import io.github.janmalch.kino.api.boundary.PriceCategoryResource;
import io.github.janmalch.kino.security.AuthorizationFilter;
import io.swagger.jaxrs.config.BeanConfig;
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/")
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

    register(PingResource.class);
    register(AccountResource.class);
    register(AuthorizationFilter.class);
    register(AuthResource.class);
    register(PriceCategoryResource.class);
    register(ValidationExceptionMapper.class);
    register(new CORSFilter());

    register(io.swagger.jaxrs.listing.ApiListingResource.class);
    register(io.swagger.jaxrs.listing.SwaggerSerializers.class);
  }
}
