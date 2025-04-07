package br.com.chronos.core.modules.collaboration.domain.exceptions;

import br.com.chronos.core.modules.global.domain.exceptions.NotPermitException;

public class NotSameCollaborationSectorExeception extends NotPermitException {
  public NotSameCollaborationSectorExeception() {
    super("Esse colaborador pertence a um setor diferente");
  }
}
