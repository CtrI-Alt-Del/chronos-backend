package br.com.chronos.core.hour_bank.domain.events;

import java.time.LocalDate;
import java.time.LocalTime;

import br.com.chronos.core.global.domain.abstracts.Event;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.hour_bank.domain.records.HourBankTransaction;

public class HourBankTransactionCreatedEvent extends Event<HourBankTransactionCreatedEvent.Payload> {
  public static final String KEY = "hour.bank/transaction.created";

  public static record Payload(
      LocalTime time,
      LocalDate date,
      boolean isCreditOperation,
      String collaboratorId) {
  }

  public HourBankTransactionCreatedEvent(HourBankTransaction transaction, Id collaboratorId) {
    super(new Payload(
        transaction.time().value(),
        transaction.date().value(),
        transaction.operation().isCreditOperation().value(),
        collaboratorId.toString()));
  }
}
