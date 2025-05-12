package br.com.chronos.core.global.domain.records;

import br.com.chronos.core.global.domain.exceptions.ValidationException;

public record Password(Text text) {
  public static Password create(String value) {
    var text = Text.create(value, "senha");

    if (text.value().length() < 6) {
      throw new ValidationException(
          "campo de senha",
          "A senha deve conter pelo menos ter 6 caracteres");
    }
    return new Password(text);
  }

  public String value() {
    return this.text.value();
  }

  public Logical matches(Password password) {
    return Logical.create(this.text.value().equals(password.text.value()));
  }
}
