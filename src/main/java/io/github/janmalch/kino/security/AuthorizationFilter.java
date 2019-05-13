package io.github.janmalch.kino.security;

import io.github.janmalch.kino.api.ResponseResultBuilder;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.repository.specification.AccountByEmailSpec;
import io.jsonwebtoken.MalformedJwtException;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
@Priority(Priorities.AUTHENTICATION)
@Secured
public class AuthorizationFilter implements ContainerRequestFilter {

  private Logger log = LoggerFactory.getLogger(AuthorizationFilter.class);
  private ResponseResultBuilder<Void> responseBuilder = new ResponseResultBuilder<>();
  private JwtTokenBlacklist blacklist = JwtTokenBlacklist.getInstance();
  private Problem unAuthProblem = Problem.builder(Response.Status.UNAUTHORIZED).instance().build();

  @Override
  public void filter(ContainerRequestContext requestContext) {
    var authHeader = requestContext.getHeaderString("Authorization");
    // abort if auth header is missing
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      log.info("Invalid Auth Header " + authHeader);
      requestContext.abortWith(responseBuilder.failure(unAuthProblem));
      return;
    }

    String tokenString = authHeader.substring(7);
    Token token = parseToken(tokenString);
    // abort if token cannot be parsed
    if (token == null) {
      log.info("Invalid Token: " + tokenString);
      requestContext.abortWith(responseBuilder.failure(unAuthProblem));
      return;
    }

    if (blacklist.isBlacklisted(token)) {
      log.info("Token is blacklisted: " + tokenString);
      requestContext.abortWith(responseBuilder.failure(unAuthProblem));
      return;
    }

    // abort if user does not exist
    if (!userExists(token)) {
      requestContext.abortWith(responseBuilder.failure(unAuthProblem));
      return;
    }

    requestContext.setSecurityContext(new TokenSecurityContext(token));
  }

  boolean userExists(Token token) {
    var repository = RepositoryFactory.createRepository(Account.class);

    var query = new AccountByEmailSpec(token.getName());
    var referredUser = repository.queryFirst(query);

    return referredUser.isPresent();
  }

  Token parseToken(String tokenString) {
    TokenFactory factory = new JwtTokenFactory();
    Token token = null;

    try {
      token = factory.parse(tokenString); // get token from requestContext
    } catch (InvalidJwtException | MalformedClaimException | MalformedJwtException e) {
      log.warn("Received invalid token", e);
    }

    return token;
  }
}
