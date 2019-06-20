package io.github.janmalch.kino.api;

public final class SuccessMessage {

  private String message;
  private final String type = "http://localhost:8080/kino/success/message";

  public SuccessMessage() {}

  public SuccessMessage(String message) {
    this.message = message;
  }

  public String getType() {
    return this.type;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
