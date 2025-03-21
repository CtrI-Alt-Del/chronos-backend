package br.com.chronos.core.modules.global.domain.exceptions;

public class ConflictException extends AppException {

  public ConflictException(String message) {
    super("Conflict Exception", message);
  }

}
