package br.com.chronos.core.global.domain.exceptions;

public class ValidationException extends AppException {
  public ValidationException(String key, String message) {
    super("Validation Exception", key + " " + message);
  }
}