package br.com.chronos.core.modules.auth.domain.exceptions;

import br.com.chronos.core.modules.global.domain.exceptions.AppException;

public class NotAuthenticatedException extends AppException {
  public NotAuthenticatedException(String message){
    super("Not authenticated exception",message);
  }
}
