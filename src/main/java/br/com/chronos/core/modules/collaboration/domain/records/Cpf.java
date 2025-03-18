package br.com.chronos.core.modules.collaboration.domain.records;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.chronos.core.modules.global.domain.exceptions.ValidationException;
import br.com.chronos.core.modules.global.domain.records.Text;

public record Cpf(Text text) {
  private static final Pattern CPF_PATTERN = Pattern.compile("^\\d{11}$");

  public static Cpf create(String value, String key) {
    var text = Text.create(value, key);
    Matcher matcher = CPF_PATTERN.matcher(text.value());
    if (!matcher.matches()) {
      throw new ValidationException(key, value + " is not valid");

    }
    return new Cpf(text);
  }
  public String value(){
    return this.text.value();
  }
}
