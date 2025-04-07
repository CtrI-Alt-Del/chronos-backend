package br.com.chronos.core.work_schedule.domain.exceptions;

import br.com.chronos.core.global.domain.exceptions.ConflictException;

public class WorkScheduleWithActiveCollaboratorDeletionException extends ConflictException {

  public WorkScheduleWithActiveCollaboratorDeletionException() {
    super("Escala de trabalho que possua colaboradores associados a ela não pode ser deletada");
  }

}
