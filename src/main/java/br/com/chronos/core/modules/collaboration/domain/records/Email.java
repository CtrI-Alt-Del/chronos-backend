package br.com.chronos.core.modules.collaboration.domain.records;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.chronos.core.modules.global.domain.exceptions.ValidationException;
import br.com.chronos.core.modules.global.domain.records.Text;

public record Email(Text text) {
  private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
      Pattern.CASE_INSENSITIVE);

  public static Email create(String value, String key) {
    var text = Text.create(value, key);
    Matcher matcher = EMAIL_PATTERN.matcher(text.value());
    if (!matcher.matches()) {
      throw new ValidationException(key, value + "is not");
    }
    return new Email(text);
  }

  public String value() {
    return this.text.value();
  }
}
