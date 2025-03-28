package br.com.chronos.core.modules.global.domain.records;

import br.com.chronos.core.modules.global.domain.exceptions.ValidationException;

public record PageNumber(PlusInteger number) {
  private static String KEY = "número da paginação";

  public static PageNumber create(int value) {
    var number = PlusInteger.create(value, KEY);

    if (number.value() == 0) {
      throw new ValidationException(KEY, "deve ser maior que zero");
    }

    return new PageNumber(number);
  }
}
