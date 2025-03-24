package br.com.chronos.core.modules.work_schedule.domain.exceptions;

import br.com.chronos.core.modules.global.domain.exceptions.NotPermitException;

public class TimePunchNotScheduledException extends NotPermitException {
  public TimePunchNotScheduledException() {
    super("Ponto não vinculado a uma escala de trabalho");
  }
}
