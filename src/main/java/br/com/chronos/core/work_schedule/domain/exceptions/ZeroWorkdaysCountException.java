package br.com.chronos.core.work_schedule.domain.exceptions;

import br.com.chronos.core.global.domain.exceptions.ValidationException;

public class ZeroWorkdaysCountException extends ValidationException {
  public ZeroWorkdaysCountException() {
    super("contagem de dias de trabalho", "deve ser maior que zero");
  }
}
