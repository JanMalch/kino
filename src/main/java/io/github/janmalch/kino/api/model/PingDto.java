package io.github.janmalch.kino.api.model;

public class PingDto {
  private String response = "pong";

  public String getResponse() {
    return response;
  }

  public void setResponse(String response) {
    this.response = response;
  }
}
