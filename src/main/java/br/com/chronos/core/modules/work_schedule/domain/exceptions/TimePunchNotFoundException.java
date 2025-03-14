package br.com.chronos.core.modules.work_schedule.domain.exceptions;

import br.com.chronos.core.modules.global.domain.exceptions.NotFoundException;

public class TimePunchNotFoundException extends NotFoundException {

  public TimePunchNotFoundException() {
    super("Time punch not found");
  }

}
