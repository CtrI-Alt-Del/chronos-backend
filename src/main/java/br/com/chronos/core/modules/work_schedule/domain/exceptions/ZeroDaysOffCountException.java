package br.com.chronos.core.modules.work_schedule.domain.exceptions;

import br.com.chronos.core.modules.global.domain.exceptions.ValidationException;

public class ZeroDaysOffCountException extends ValidationException {
  public ZeroDaysOffCountException() {
    super("contagem de folgas", "deve ser maior que zero");
  }
}
