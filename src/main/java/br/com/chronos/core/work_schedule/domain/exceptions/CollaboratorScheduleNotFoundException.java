package br.com.chronos.core.work_schedule.domain.exceptions;

import br.com.chronos.core.global.domain.exceptions.NotFoundException;

public class CollaboratorScheduleNotFoundException extends NotFoundException {
  public CollaboratorScheduleNotFoundException() {
    super("Escala de trabalho para esse trabalhado não encontrada");
  }
}
