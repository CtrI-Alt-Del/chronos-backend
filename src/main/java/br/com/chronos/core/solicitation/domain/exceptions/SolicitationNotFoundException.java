package br.com.chronos.core.solicitation.domain.exceptions;

import br.com.chronos.core.global.domain.exceptions.NotFoundException;

public class SolicitationNotFoundException extends NotFoundException {
  public SolicitationNotFoundException() {
    super("Solicitation não encontrada");
  }
}
