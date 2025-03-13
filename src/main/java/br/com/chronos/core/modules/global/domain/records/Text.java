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

  public Logical equalsTo(String value) {
    return Logical.create(this.value.equals(value));
  }

  public Logical notEqualsTo(String value) {
    return Logical.create(!this.value.equals(value));
  }

}
