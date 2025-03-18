package br.com.chronos.core.modules.work_schedule.domain.records;

import br.com.chronos.core.modules.global.domain.exceptions.ValidationException;

public record PlusInteger(int value) {
  public static PlusInteger create(int value, String key) {
    if (value < 0) {
      throw new ValidationException(key, "deve ser um inteiro positivo");
    }

    return new PlusInteger(value);
  }
}
