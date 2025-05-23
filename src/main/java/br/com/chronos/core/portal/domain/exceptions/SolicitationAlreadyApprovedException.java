package br.com.chronos.core.portal.domain.exceptions;

import br.com.chronos.core.global.domain.exceptions.ConflictException;

public class SolicitationAlreadyApprovedException extends ConflictException {
  public SolicitationAlreadyApprovedException() {
    super("Solicitação já aprovada");
  }
}
