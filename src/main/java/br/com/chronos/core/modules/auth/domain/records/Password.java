package br.com.chronos.core.modules.auth.domain.records;

import java.util.regex.Pattern;

import br.com.chronos.core.modules.global.domain.exceptions.ValidationException;
import br.com.chronos.core.modules.global.domain.records.Text;

public record Password(Text text) {
  private static final Pattern PASSWORD_PATTERN = Pattern
      .compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{6,20}$");

  public static Password create(String value) {
    var text = Text.create(value, "senha");
    if (text.value() instanceof String) {
      return new Password(text);
    }
    throw new ValidationException("senha", "é obrigatório");
  }

  public String value() {
    return this.text.value();
  }
}
