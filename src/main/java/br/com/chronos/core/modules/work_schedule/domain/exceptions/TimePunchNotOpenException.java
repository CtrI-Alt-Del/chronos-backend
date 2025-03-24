package br.com.chronos.core.modules.work_schedule.domain.exceptions;

import br.com.chronos.core.modules.global.domain.exceptions.NotPermitException;

public class TimePunchNotOpenException extends NotPermitException {
  public TimePunchNotOpenException() {
    super("Ponto fechado");
  }
}
