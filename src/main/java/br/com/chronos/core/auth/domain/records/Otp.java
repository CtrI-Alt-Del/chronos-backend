package br.com.chronos.core.auth.domain.records;

import java.util.concurrent.ThreadLocalRandom;

import br.com.chronos.core.global.domain.exceptions.ValidationException;
import br.com.chronos.core.global.domain.records.PlusIntegerNumber;
import br.com.chronos.core.global.domain.records.Text;

public record Otp(Text code) {
  public static final int MINUTES_TO_EXPIRE = 60;

  public static Otp create(String codeValue) {
    var code = Text.create(codeValue, "código de autenticação");

    if (code.charactersCount().isEqual(PlusIntegerNumber.create(6)).isFalse()) {
      throw new ValidationException("código de autenticação", "deve conter 6 dígitos");
    }
    return new Otp(code);
  }

  public static Otp create() {
    var code = String.format("%06d", ThreadLocalRandom.current().nextInt(0, 1000000));
    return new Otp(Text.create(code, "otp code"));
  }
}
