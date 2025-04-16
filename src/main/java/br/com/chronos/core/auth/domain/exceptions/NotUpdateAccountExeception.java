package br.com.chronos.core.auth.domain.exceptions;

import br.com.chronos.core.global.domain.exceptions.NotPermitException;

public class NotUpdateAccountExeception extends NotPermitException {
  public NotUpdateAccountExeception() {
    super("Não é permitido atualizar essa conta");
  }
}
