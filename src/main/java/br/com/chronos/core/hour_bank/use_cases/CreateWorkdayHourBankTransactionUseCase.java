package br.com.chronos.core.hour_bank.use_cases;

import java.time.LocalDate;
import java.time.LocalTime;

import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.Time;
import br.com.chronos.core.hour_bank.domain.dtos.HourBankTransactionDto;
import br.com.chronos.core.hour_bank.domain.records.HourBankTransaction;
import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;

public class CreateWorkdayHourBankTransactionUseCase {
  private final HourBankTransactionsRepository repository;

  public CreateWorkdayHourBankTransactionUseCase(HourBankTransactionsRepository repository) {
    this.repository = repository;
  }

  public void execute(
      LocalTime overtime,
      LocalTime undertime,
      LocalTime latetime,
      LocalDate date,
      String collaboratorId) {
    var creditTime = Time.create(overtime);
    var debitTime = Time.create(undertime).plus(Time.create(latetime));

    if (creditTime.isGreaterThan(debitTime).isTrue()) {
      var dto = new HourBankTransactionDto()
          .setTime(creditTime.minus(debitTime).value())
          .setDate(date)
          .setOperation("CREDIT")
          .setReason("OVERTIME");

      var transaction = HourBankTransaction.create(dto);
      repository.add(transaction, Id.create(collaboratorId));
      return;
    }

    if (debitTime.isGreaterThan(creditTime).isTrue()) {
      var dto = new HourBankTransactionDto()
          .setTime(debitTime.getDifferenceFrom(creditTime).value())
          .setDate(date)
          .setOperation("DEBIT")
          .setReason("LATETIME");

      var transaction = HourBankTransaction.create(dto);
      repository.add(transaction, Id.create(collaboratorId));
    }
  }
}
