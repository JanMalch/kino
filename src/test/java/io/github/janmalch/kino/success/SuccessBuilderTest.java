package io.github.janmalch.kino.success;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.junit.jupiter.api.Test;

class SuccessBuilderTest {

  @Test
  public void allFields() {
    URI baseUri = Success.DEFAULT_TYPE;
    Success success =
        Success.builder()
            .message("A movie was added")
            .status(Response.Status.OK)
            .data("Movie 1 was added")
            .instance(UriBuilder.fromUri(baseUri).matrixParam("timestamp", "test").build())
            .build();

    assertEquals("A movie was added", success.getMessage());
    assertEquals(Response.Status.OK, success.getStatus());
    assertEquals("Movie 1 was added", success.getData());
    assertEquals(
        UriBuilder.fromUri(baseUri).matrixParam("timestamp", "test").build(),
        success.getInstance());
  }

  @Test
  public void toStringPrintsCorrectString() {
    URI baseUri = Success.DEFAULT_TYPE;
    Success success =
        Success.builder()
            .message("A movie was added")
            .status(Response.Status.OK)
            .data("Movie 1 was added")
            .instance(UriBuilder.fromUri(baseUri).matrixParam("timestamp", "test").build())
            .build();

    assertEquals(
        "SuccessImpl{message=A movie was added, status='OK', data=Movie 1 was added', instance=http://localhost:8080/kino;timestamp=test}",
        success.toString());
  }

  @Test
  public void autoInstance() {
    Success success = Success.builder().instance().build();
    assertTrue(success.getInstance().toString().contains("time"));
  }
}
