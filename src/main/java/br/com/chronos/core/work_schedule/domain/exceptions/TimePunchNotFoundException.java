package br.com.chronos.core.work_schedule.domain.exceptions;

import br.com.chronos.core.global.domain.exceptions.NotFoundException;

public class TimePunchNotFoundException extends NotFoundException {
  public TimePunchNotFoundException() {
    super("Time punch not found");
  }
}
