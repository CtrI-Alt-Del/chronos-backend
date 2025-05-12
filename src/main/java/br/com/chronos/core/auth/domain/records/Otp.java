package br.com.chronos.core.auth.domain.records;

import java.util.concurrent.ThreadLocalRandom;

import br.com.chronos.core.global.domain.records.Text;

public record Otp(Text code) {

  public static final int MINUTES_TO_EXPIRE = 60;

  public static Otp create(String code) {
    return new Otp(Text.create(code, "otp code"));
  }

  public static Otp create() {
    var code = String.format("%04d", ThreadLocalRandom.current().nextInt(0, 10000));
    return new Otp(Text.create(code, "otp code"));
  }

}
