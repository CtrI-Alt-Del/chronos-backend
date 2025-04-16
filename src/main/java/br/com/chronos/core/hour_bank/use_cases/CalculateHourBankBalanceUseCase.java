package br.com.chronos.core.hour_bank.use_cases;

import java.time.LocalTime;

import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.Time;
import br.com.chronos.core.hour_bank.domain.records.HourBankTransaction;
import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;

public class CalculateHourBankBalanceUseCase {
  private final HourBankTransactionsRepository repository;

  public CalculateHourBankBalanceUseCase(HourBankTransactionsRepository repository) {
    this.repository = repository;
  }

  public LocalTime execute(String collaboratorId) {
    var transactions = repository.findAllByCollaborator(Id.create(collaboratorId));
    var balanceTime = Time.createAsZero();
    var creditTime = Time.createAsZero();
    var debitTime = Time.createAsZero();

    for (HourBankTransaction transaction : transactions.list()) {
      if (transaction.operation().isCreditOperation().isTrue()) {
        debitTime = debitTime.plus(transaction.time());
      } else {
        debitTime = debitTime.plus(transaction.time());
      }
    }

    balanceTime = balanceTime.plus(creditTime).minus(debitTime);
    return balanceTime.value();
  }
}
