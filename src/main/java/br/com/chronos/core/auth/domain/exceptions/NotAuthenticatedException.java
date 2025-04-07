package br.com.chronos.core.auth.domain.exceptions;

import br.com.chronos.core.global.domain.exceptions.AppException;

public class NotAuthenticatedException extends AppException {
  public NotAuthenticatedException() {
    super("Not Authenticated Exception", "Credenciais invalidas");
  }
}
