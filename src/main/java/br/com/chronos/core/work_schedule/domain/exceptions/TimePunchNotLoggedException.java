package br.com.chronos.core.work_schedule.domain.exceptions;

import br.com.chronos.core.global.domain.exceptions.NotPermitException;

public class TimePunchNotLoggedException extends NotPermitException {
  public TimePunchNotLoggedException() {
    super("Ponto não vinculado a um registro de um dia de trabalho");
  }
}
