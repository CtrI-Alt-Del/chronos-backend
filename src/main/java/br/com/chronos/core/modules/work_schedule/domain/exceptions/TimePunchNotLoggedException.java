package br.com.chronos.core.modules.work_schedule.domain.exceptions;

import br.com.chronos.core.modules.global.domain.exceptions.NotPermitException;

public class TimePunchNotLoggedException extends NotPermitException {
  public TimePunchNotLoggedException() {
    super("Ponto não vinculado a um registro de um dia de trabalho");
  }
}
