package io.github.janmalch.kino.api;

import io.github.janmalch.kino.api.boundary.PingResource;
import io.swagger.jaxrs.config.BeanConfig;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
public class WebApplication extends Application {

  public WebApplication() {
    BeanConfig beanConfig = new BeanConfig();
    beanConfig.setTitle("Kino API");
    beanConfig.setVersion("1.0.0");
    beanConfig.setSchemes(new String[] {"http"});
    beanConfig.setHost("localhost:8080");
    beanConfig.setBasePath("/kino/api");
    beanConfig.setResourcePackage("io.github.janmalch.kino.api.boundary");
    beanConfig.setScan(true);
  }

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> resources = new HashSet<>();

    resources.add(PingResource.class);

    resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
    resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);

    return resources;
  }
}
