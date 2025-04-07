package br.com.chronos.core.global.domain.records;

import br.com.chronos.core.global.domain.exceptions.ValidationException;

public record PlusInteger(int value) {
  public static PlusInteger create(int value, String key) {
    if (value < 0) {
      throw new ValidationException(key, "deve ser um inteiro positivo");
    }

    return new PlusInteger(value);
  }

  public static PlusInteger create(int value) {
    if (value < 0) {
      throw new ValidationException(String.valueOf(value), "deve ser um inteiro positivo");
    }

    return new PlusInteger(value);
  }

  public PlusInteger plus(int otherValue) {
    return new PlusInteger(value + otherValue);
  }

  public Logical isEqual(PlusInteger integer) {
    return Logical.create(value == integer.value);
  }
}
