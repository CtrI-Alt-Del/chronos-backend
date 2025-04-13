package br.com.chronos.core.work_schedule.domain.exceptions;

import br.com.chronos.core.global.domain.exceptions.ValidationException;

public class ZeroDaysOffCountException extends ValidationException {
  public ZeroDaysOffCountException() {
    super("contagem de folgas", "deve ser maior que zero");
  }
}
