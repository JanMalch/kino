package io.github.janmalch.kino.security;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RefreshTokenResponseFilter implements ContainerResponseFilter {

  private final Logger logger = LoggerFactory.getLogger(RefreshTokenResponseFilter.class);
  private final TokenFactory tokenFactory = new JwtTokenFactory();

  @Override
  public void filter(
      ContainerRequestContext requestContext, ContainerResponseContext responseContext)
      throws IOException {
    if (requestContext.getUriInfo().getAbsolutePath().toString().contains("logout")) {
      // do not refresh on logout
      return;
    }

    // TODO: refactor so that token gets refreshed every time it is included in the header?
    //  by taking it from the security context this only works for secured routes
    var principal = requestContext.getSecurityContext().getUserPrincipal();
    if (principal != null) {
      try {
        var token = (Token) principal;
        if (token.isExpired()) {
          return;
        }
        var refreshedToken = tokenFactory.refresh(token);
        responseContext.getHeaders().add("Token", refreshedToken.getTokenString());
      } catch (ClassCastException | MalformedClaimException | InvalidJwtException e) {
        logger.warn("Unable to refresh token", e);
      }
    }
  }
}
