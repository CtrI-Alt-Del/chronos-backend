package br.com.chronos.core.collaboration.domain.exceptions;

import br.com.chronos.core.global.domain.exceptions.NotPermitException;

public class NotSameCollaborationSectorExeception extends NotPermitException {
  public NotSameCollaborationSectorExeception() {
    super("Esse colaborador pertence a um setor diferente");
  }
}
