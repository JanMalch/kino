package io.github.janmalch.kino.security;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RefreshTokenResponseFilter implements ContainerResponseFilter {

  /** refresh tokens within 10 minutes of expiring */
  private final long refreshTimeFrame = TimeUnit.MINUTES.toMillis(10);

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

        // do not refresh if token is not expired
        if (token.isExpired()) {
          return;
        }

        var refreshTimeFrameStart = new Date(token.getExpiration().getTime() - refreshTimeFrame);
        var closeToExpiration = refreshTimeFrameStart.before(new Date());

        // do not refresh if token does not expired within next 15 minutes
        if (!closeToExpiration) {
          return;
        }

        var refreshedToken = tokenFactory.refresh(token);
        responseContext.getHeaders().add("Token", refreshedToken.getTokenString());
        // make token available for frontend
        responseContext.getHeaders().add("Access-Control-Expose-Headers", "Token");
      } catch (ClassCastException | MalformedClaimException | InvalidJwtException e) {
        logger.warn("Unable to refresh token", e);
      }
    }
  }
}
