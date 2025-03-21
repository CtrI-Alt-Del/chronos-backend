package br.com.chronos.core.modules.auth.domain.exceptions;

import br.com.chronos.core.modules.global.domain.exceptions.NotPermitException;

public class DisabledAccountException extends NotPermitException {
  public DisabledAccountException(){
    super("Conta desativada");
  }
}
