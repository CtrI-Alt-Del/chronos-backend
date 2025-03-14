package br.com.chronos.core.modules.global.domain.exceptions;

public class NotFoundException extends AppException {

  public NotFoundException(String message) {
    super("Not Found Exeception", message);
  }

}
