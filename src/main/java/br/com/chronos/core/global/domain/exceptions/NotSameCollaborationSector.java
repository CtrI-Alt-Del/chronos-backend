package br.com.chronos.core.global.domain.exceptions;

public class NotSameCollaborationSector extends NotPermitException {
  public NotSameCollaborationSector() {
    super("Colaboradores não pertencem ao mesmo setor");
  }
}
