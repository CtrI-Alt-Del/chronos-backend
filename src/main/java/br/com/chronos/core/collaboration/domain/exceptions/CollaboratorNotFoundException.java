package br.com.chronos.core.collaboration.domain.exceptions;

import br.com.chronos.core.global.domain.exceptions.NotFoundException;

public class CollaboratorNotFoundException extends NotFoundException {
  public CollaboratorNotFoundException() {
    super("Colaborador não encontrado");
  }
}
