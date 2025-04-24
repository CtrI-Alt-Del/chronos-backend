package br.com.chronos.core.hour_bank.domain.records.fakers;

import java.util.List;

import com.github.javafaker.Faker;

import br.com.chronos.core.hour_bank.domain.records.HourBankTransactionOperation;

public class HourBankTransactionOperationFaker {
  private static Faker faker = new Faker();

  public static HourBankTransactionOperation fake() {
    return HourBankTransactionOperation.create(fakeDto());
  }

  public static String fakeDto() {
    return faker.options().nextElement(List.of("CREDIT", "DEBIT"));
  }
}
