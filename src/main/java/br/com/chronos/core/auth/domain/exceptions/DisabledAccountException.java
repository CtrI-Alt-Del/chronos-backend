package br.com.chronos.core.auth.domain.exceptions;

import br.com.chronos.core.global.domain.exceptions.NotPermitException;

public class DisabledAccountException extends NotPermitException {
  public DisabledAccountException() {
    super("Conta desativada");
  }
}
