package br.com.chronos.core.modules.global.domain.exceptions;

public class AppException extends RuntimeException {
  private final String title;
  private final String message;

  public AppException(String message) {
    super(message);
    this.title = "App exception";
    this.message = message;
  }

  public AppException(String title, String message) {
    super(message);
    this.title = title;
    this.message = message;
  }

  public String getTitle() {
    return title;
  }

  public String getMessage() {
    return message;
  }
}
