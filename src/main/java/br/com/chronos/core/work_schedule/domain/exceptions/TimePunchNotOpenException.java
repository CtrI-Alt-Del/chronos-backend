package br.com.chronos.core.work_schedule.domain.exceptions;

import br.com.chronos.core.global.domain.exceptions.NotPermitException;

public class TimePunchNotOpenException extends NotPermitException {
  public TimePunchNotOpenException() {
    super("Ponto fechado");
  }
}
