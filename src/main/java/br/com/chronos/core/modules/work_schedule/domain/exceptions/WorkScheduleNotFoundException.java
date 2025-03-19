package br.com.chronos.core.modules.work_schedule.domain.exceptions;

import br.com.chronos.core.modules.global.domain.exceptions.NotFoundException;

public class WorkScheduleNotFoundException extends NotFoundException {
  public WorkScheduleNotFoundException() {
    super("Escala de trabalho não encontrada");
  }
}
