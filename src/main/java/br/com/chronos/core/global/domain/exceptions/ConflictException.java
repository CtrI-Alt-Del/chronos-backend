package br.com.chronos.core.global.domain.exceptions;

public class ConflictException extends AppException {

  public ConflictException(String message) {
    super("Conflict Exception", message);
  }

}
