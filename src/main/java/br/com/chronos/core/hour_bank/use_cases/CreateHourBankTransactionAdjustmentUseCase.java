package br.com.chronos.core.hour_bank.use_cases;

import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.hour_bank.domain.dtos.HourBankTransactionDto;
import br.com.chronos.core.hour_bank.domain.records.HourBankTransaction;
import br.com.chronos.core.hour_bank.domain.records.HourBankTransactionReason;
import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;

public class CreateHourBankTransactionAdjustmentUseCase {
  private final HourBankTransactionsRepository repository;

  public CreateHourBankTransactionAdjustmentUseCase(HourBankTransactionsRepository repository) {
    this.repository = repository;
  }

  public void execute(HourBankTransactionDto hourBankTransactionDto, String collaboratorId) {
    hourBankTransactionDto.setReason(HourBankTransactionReason.createAsAdjustment().toString());
    var hourBankTransaction = HourBankTransaction.create(hourBankTransactionDto);
    repository.add(hourBankTransaction, Id.create(collaboratorId));
  }
}
