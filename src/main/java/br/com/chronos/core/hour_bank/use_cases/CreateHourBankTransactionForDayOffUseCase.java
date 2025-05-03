package br.com.chronos.core.hour_bank.use_cases;

import java.time.LocalTime;

import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.hour_bank.domain.dtos.HourBankTransactionDto;
import br.com.chronos.core.hour_bank.domain.records.HourBankTransaction;
import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;

public class CreateHourBankTransactionForDayOffUseCase {
  private final HourBankTransactionsRepository repository;

  public CreateHourBankTransactionForDayOffUseCase(HourBankTransactionsRepository repository) {
    this.repository = repository;
  }

  public void execute(String collaboratorId, int collaboratorWorkload) {
    var dto = new HourBankTransactionDto()
        .setTime(LocalTime.of(collaboratorWorkload, 0))
        .setOperation("DEBIT")
        .setReason("DAY_OFF");
    var transaction = HourBankTransaction.create(dto);
    repository.add(transaction, Id.create(collaboratorId));
  }
}
