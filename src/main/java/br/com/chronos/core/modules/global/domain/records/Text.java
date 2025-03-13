package br.com.chronos.core.modules.global.domain.records;

import br.com.chronos.core.modules.global.domain.exceptions.ValidationException;

public record Text(String value, String key) {
  public static Text create(String value, String key) {
    if (value instanceof String) {
      return new Text(value, key);
    }
    throw new ValidationException(key, "is required");
  }

  public Text update(String value) {
    return Text.create(value, key);
  }

  public boolean equalsTo(String value) {
    return this.value.equals(value);
  }

  public boolean notEqualsTo(String value) {
    return !this.value.equals(value);
  }

}
