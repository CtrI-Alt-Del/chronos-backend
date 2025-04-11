package br.com.chronos.core.global.domain.records;

import br.com.chronos.core.global.domain.exceptions.ValidationException;

public record PageNumber(PlusIntegerNumber number) {
  private static String KEY = "número da paginação";

  public static PageNumber create(int value) {
    var number = PlusIntegerNumber.create(value, KEY);

    if (number.value() == 0) {
      throw new ValidationException(KEY, "deve ser maior que zero");
    }

    return new PageNumber(number);
  }
}
