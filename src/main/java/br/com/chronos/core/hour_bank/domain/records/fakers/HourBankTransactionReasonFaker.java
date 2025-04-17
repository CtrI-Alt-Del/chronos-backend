package br.com.chronos.core.hour_bank.domain.records.fakers;

import java.util.List;

import com.github.javafaker.Faker;

import br.com.chronos.core.hour_bank.domain.records.HourBankTransactionReason;

public class HourBankTransactionReasonFaker {
  private static Faker faker = new Faker();

  public static HourBankTransactionReason fake() {
    return HourBankTransactionReason.create(fakeDto());
  }

  public static String fakeDto() {
    return faker.options().nextElement(List.of("OVERTIME", "LATETIME", "ADJUSTMENT"));
  }
}
