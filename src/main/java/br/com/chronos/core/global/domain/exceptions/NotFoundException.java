package br.com.chronos.core.global.domain.exceptions;

public class NotFoundException extends AppException {

  public NotFoundException(String message) {
    super("Not Found Exeception", message);
  }

}
