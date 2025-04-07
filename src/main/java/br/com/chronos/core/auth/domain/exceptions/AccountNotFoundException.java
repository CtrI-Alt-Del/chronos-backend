package br.com.chronos.core.auth.domain.exceptions;

import br.com.chronos.core.global.domain.exceptions.NotFoundException;

public class AccountNotFoundException extends NotFoundException {
  public AccountNotFoundException() {
    super("Conta nao encontrada");
  }
}
