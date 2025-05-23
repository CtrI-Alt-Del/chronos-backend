package br.com.chronos.core.global.domain.records;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.chronos.core.global.domain.exceptions.ValidationException;

public record Email(Text text) {
  private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
      Pattern.CASE_INSENSITIVE);

  public static Email create(String value) {
    var text = Text.create(value, "e-mail");
    Matcher matcher = EMAIL_PATTERN.matcher(text.value());
    if (!matcher.matches()) {
      throw new ValidationException("e-mail", value + " is not an valid email");
    }
    return new Email(text);
  }

  public String value() {
    return this.text.value();
  }
}
