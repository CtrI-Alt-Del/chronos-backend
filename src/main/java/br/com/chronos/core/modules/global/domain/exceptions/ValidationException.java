package br.com.chronos.core.modules.global.domain.exceptions;

public class ValidationException extends AppException {
  public ValidationException(String key, String message) {
    super("Validation Exception", key + " " + message);
  }
}