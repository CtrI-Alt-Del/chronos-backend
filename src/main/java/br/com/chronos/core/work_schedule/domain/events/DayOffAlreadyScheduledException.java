package br.com.chronos.core.work_schedule.domain.events;

import br.com.chronos.core.global.domain.exceptions.ConflictException;

public class DayOffAlreadyScheduledException extends ConflictException {
  public DayOffAlreadyScheduledException() {
    super("Folga já escalada");
  }
}
