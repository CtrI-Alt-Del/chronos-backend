package br.com.chronos.core.global.domain.records;

import br.com.chronos.core.global.domain.exceptions.ValidationException;

public record IntegerNumber(int value) {
  public static IntegerNumber create(int value, String key) {
    if (value < 0) {
      throw new ValidationException(key, "deve ser um inteiro");
    }

    return new IntegerNumber(value);
  }

  public IntegerNumber plus(IntegerNumber integer) {
    return new IntegerNumber(this.value + integer.value);
  }

  public IntegerNumber minus(IntegerNumber integer) {
    return new IntegerNumber(this.value - integer.value);
  }
}
