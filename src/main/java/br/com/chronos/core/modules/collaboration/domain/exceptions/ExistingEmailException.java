package br.com.chronos.core.modules.collaboration.domain.exceptions;

import br.com.chronos.core.modules.global.domain.exceptions.ConflictException;

public class ExistingEmailException extends ConflictException {
  public ExistingEmailException() {
    super("Ja tem um usuario com esse e-mail");
  }
}
