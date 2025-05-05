package br.com.chronos.core.work_schedule.domain.events;

import br.com.chronos.core.global.domain.exceptions.NotFoundException;

public class DayOffScheduleNotFoundException extends NotFoundException {
  public DayOffScheduleNotFoundException() {
    super("Escala de folga não encontrada");
  }
}
