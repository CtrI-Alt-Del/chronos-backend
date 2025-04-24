package br.com.chronos.core.hour_bank.use_cases;

import java.time.LocalDate;
import java.time.LocalTime;

import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.Time;
import br.com.chronos.core.hour_bank.domain.dtos.HourBankTransactionDto;
import br.com.chronos.core.hour_bank.domain.events.HourBankTransactionCreatedEvent;
import br.com.chronos.core.hour_bank.domain.records.HourBankTransaction;
import br.com.chronos.core.hour_bank.interfaces.HourBankBroker;
import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;

public class CreateWorkdayHourBankTransactionUseCase {
  private final HourBankTransactionsRepository repository;
  private final HourBankBroker broker;

  public CreateWorkdayHourBankTransactionUseCase(
      HourBankTransactionsRepository repository,
      HourBankBroker broker) {
    this.repository = repository;
    this.broker = broker;
  }

  public void execute(
      LocalTime overtime,
      LocalTime undertime,
      LocalTime latetime,
      LocalDate date,
      String collaboratorIdValue) {
    var creditTime = Time.create(overtime);
    var debitTime = Time.create(undertime).plus(Time.create(latetime));
    var collaboratorId = Id.create(collaboratorIdValue);

    if (creditTime.isGreaterThan(debitTime).isTrue()) {
      var dto = new HourBankTransactionDto()
          .setTime(creditTime.minus(debitTime).value())
          .setDate(date)
          .setOperation("CREDIT")
          .setReason("OVERTIME");

      var transaction = HourBankTransaction.create(dto);
      repository.add(transaction, collaboratorId);
      return;
    }

    if (debitTime.isGreaterThan(creditTime).isTrue()) {
      var dto = new HourBankTransactionDto()
          .setTime(debitTime.getDifferenceFrom(creditTime).value())
          .setDate(date)
          .setOperation("DEBIT")
          .setReason("LATETIME");

      var transaction = HourBankTransaction.create(dto);
      repository.add(transaction, collaboratorId);

      var event = new HourBankTransactionCreatedEvent(transaction, collaboratorId);
      broker.publish(event);
    }
  }
}
