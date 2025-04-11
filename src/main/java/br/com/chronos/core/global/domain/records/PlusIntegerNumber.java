package br.com.chronos.core.global.domain.records;

import br.com.chronos.core.global.domain.exceptions.ValidationException;

public record PlusIntegerNumber(int value) {
  public static PlusIntegerNumber create(int value, String key) {
    if (value < 0) {
      throw new ValidationException(key, "deve ser um inteiro positivo");
    }

    return new PlusIntegerNumber(value);
  }

  public static PlusIntegerNumber create(int value) {
    if (value < 0) {
      throw new ValidationException(String.valueOf(value), "deve ser um inteiro positivo");
    }

    return new PlusIntegerNumber(value);
  }

  public PlusIntegerNumber plus(int otherValue) {
    return new PlusIntegerNumber(value + otherValue);
  }

  public Logical isEqual(PlusIntegerNumber integer) {
    return Logical.create(value == integer.value);
  }
}
