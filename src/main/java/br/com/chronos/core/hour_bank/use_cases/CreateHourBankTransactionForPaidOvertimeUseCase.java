package br.com.chronos.core.hour_bank.use_cases;

import br.com.chronos.core.global.domain.records.Time;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.hour_bank.domain.dtos.HourBankTransactionDto;
import br.com.chronos.core.hour_bank.domain.records.HourBankTransaction;
import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;

public class CreateHourBankTransactionForPaidOvertimeUseCase {
  private final HourBankTransactionsRepository repository;
  private final static Time PAID_OVERTIME = Time.create(2, 0);

  public CreateHourBankTransactionForPaidOvertimeUseCase(HourBankTransactionsRepository repository) {
    this.repository = repository;
  }

  public void execute(String collaboratorId) {
    var dto = new HourBankTransactionDto()
        .setTime(PAID_OVERTIME.value())
        .setOperation("DEBIT")
        .setReason("OVERTIME");
    var transaction = HourBankTransaction.create(dto);
    repository.add(transaction, Id.create(collaboratorId));
  }
}
