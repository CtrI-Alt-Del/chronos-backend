package br.com.chronos.core.modules.collaboration.domain.records;

import java.util.regex.Pattern;

import br.com.chronos.core.modules.global.domain.exceptions.ValidationException;
import br.com.chronos.core.modules.global.domain.records.Text;

public record Password(Text text) {
  private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{6,20}$");

  public static Password create(String value, String key) {
    var text = Text.create(value, key);
    if (text.value() instanceof String) {
      return new Password(text);
    }
    throw new ValidationException(key, "is required");
  }

  public String value() {
    return this.text.value();
  }
}
