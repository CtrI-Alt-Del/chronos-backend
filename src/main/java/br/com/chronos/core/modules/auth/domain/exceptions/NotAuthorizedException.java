package br.com.chronos.core.modules.auth.domain.exceptions;

import br.com.chronos.core.modules.global.domain.exceptions.NotPermitException;

public class NotAuthorizedException extends NotPermitException {
  public NotAuthorizedException() {
    super("Permissao invalida");
  }
}
