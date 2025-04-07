package br.com.chronos.core.global.domain.records;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.chronos.core.global.domain.exceptions.ValidationException;

public record Cpf(Text text) {
  private static final Pattern CPF_PATTERN = Pattern.compile("^\\d{11}$");

  public static Cpf create(String value) {
    var text = Text.create(value, "cpf");
    Matcher matcher = CPF_PATTERN.matcher(text.value());
    if (!matcher.matches()) {
      throw new ValidationException("cpf", "não é válido");

    }
    return new Cpf(text);
  }

  public String value() {
    return this.text.value();
  }
}
