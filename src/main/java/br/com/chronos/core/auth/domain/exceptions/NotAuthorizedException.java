package br.com.chronos.core.auth.domain.exceptions;

import br.com.chronos.core.global.domain.exceptions.NotPermitException;

public class NotAuthorizedException extends NotPermitException {
  public NotAuthorizedException() {
    super("Permissao invalida");
  }
}
