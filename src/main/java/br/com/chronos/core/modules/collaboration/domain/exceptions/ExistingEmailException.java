package br.com.chronos.core.modules.collaboration.domain.exceptions;

import br.com.chronos.core.modules.global.domain.exceptions.ConflictException;

public class ExistingEmailException extends ConflictException {
  public ExistingEmailException(){
    super("There is already an user with this email");
  }
}
