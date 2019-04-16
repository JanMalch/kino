package io.github.janmalch.kino.api.boundary;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class PingResourceTest {

  @Test
  void ping() {
    var resource = new PingResource();
    var dto = resource.ping();
    assertNotNull(dto.getResponse());
  }
}
