package io.github.janmalch.kino.api;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class APIExceptionMapperTest {

  @Test
  void toResponse() {
    var mapper = new APIExceptionMapper();
    var response = mapper.toResponse(new Exception("Test Exception"));
    assertEquals(500, response.getStatus());
  }
}
