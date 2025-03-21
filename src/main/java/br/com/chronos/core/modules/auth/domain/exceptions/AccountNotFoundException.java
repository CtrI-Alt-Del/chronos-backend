package br.com.chronos.core.modules.auth.domain.exceptions;

import br.com.chronos.core.modules.global.domain.exceptions.NotFoundException;

public class AccountNotFoundException extends NotFoundException{
  public AccountNotFoundException(){
    super("Conta nao encontrada");
  }
}
