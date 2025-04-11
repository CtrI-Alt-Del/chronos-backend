package br.com.chronos.core.work_schedule.domain.exceptions;

import br.com.chronos.core.global.domain.exceptions.NotPermitException;

public class TimePunchNotScheduledException extends NotPermitException {
  public TimePunchNotScheduledException() {
    super("Ponto não vinculado a uma escala de trabalho");
  }
}
