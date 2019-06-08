package io.github.janmalch.kino.api;

import io.github.janmalch.kino.api.boundary.*;
import io.github.janmalch.kino.security.AuthorizationFilter;
import io.swagger.jaxrs.config.BeanConfig;
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

@ApplicationPath("api")
public class WebApplication extends ResourceConfig {

  public WebApplication() {
    BeanConfig beanConfig = new BeanConfig();
    beanConfig.setTitle("Kino API");
    beanConfig.setVersion("1.0.0");
    beanConfig.setSchemes(new String[] {"http"});
    beanConfig.setHost("localhost:8080");
    beanConfig.setBasePath("api");
    beanConfig.setResourcePackage("io.github.janmalch.kino.api.boundary");
    beanConfig.setScan(true);

    register(AccountResource.class);
    register(AuthResource.class);
    register(AuthorizationFilter.class);
    register(CinemaHallResource.class);
    register(MovieResource.class);
    register(MyAccountResource.class);
    register(PresentationResource.class);
    register(PriceCategoryResource.class);
    register(PingResource.class);
    register(RolesAllowedDynamicFeature.class);
    register(ValidationExceptionMapper.class);
    register(new CORSFilter());

    register(io.swagger.jaxrs.listing.ApiListingResource.class);
    register(io.swagger.jaxrs.listing.SwaggerSerializers.class);
  }
}
