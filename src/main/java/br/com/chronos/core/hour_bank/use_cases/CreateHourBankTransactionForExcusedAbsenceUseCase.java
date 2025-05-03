package br.com.chronos.core.hour_bank.use_cases;

import java.time.LocalTime;

import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.hour_bank.domain.dtos.HourBankTransactionDto;
import br.com.chronos.core.hour_bank.domain.records.HourBankTransaction;
import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;

public class CreateHourBankTransactionForExcusedAbsenceUseCase {
  private final HourBankTransactionsRepository repository;

  public CreateHourBankTransactionForExcusedAbsenceUseCase(
      HourBankTransactionsRepository repository) {
    this.repository = repository;
  }

  public void execute(String collaboratorId, LocalTime workdayHourBankDebit) {
    var dto = new HourBankTransactionDto()
        .setTime(workdayHourBankDebit)
        .setOperation("CREDIT")
        .setReason("EXCUSED_ABSENCE");
    var transaction = HourBankTransaction.create(dto);
    repository.add(transaction, Id.create(collaboratorId));
  }
}
