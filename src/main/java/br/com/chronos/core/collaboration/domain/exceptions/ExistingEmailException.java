package br.com.chronos.core.collaboration.domain.exceptions;

import br.com.chronos.core.global.domain.exceptions.ConflictException;

public class ExistingEmailException extends ConflictException {
  public ExistingEmailException() {
    super("Ja tem um usuario com esse e-mail");
  }
}
