package br.com.chronos.core.portal.domain.exceptions;

import br.com.chronos.core.global.domain.exceptions.ConflictException;

public class SolicitationAlreadyDeniedException extends ConflictException {
  public SolicitationAlreadyDeniedException() {
    super("Solicitação já negada");
  }
}
