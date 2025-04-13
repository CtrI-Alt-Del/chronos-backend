package br.com.chronos.core.hour_bank.use_cases;

import java.time.LocalTime;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.Time;
import br.com.chronos.core.hour_bank.domain.records.HourBankTransaction;
import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;

public class CreateHourBankTransactionForClosedTimePunchUseCase {
  private final HourBankTransactionsRepository repository;

  public CreateHourBankTransactionForClosedTimePunchUseCase(HourBankTransactionsRepository repository) {
    this.repository = repository;
  }

  public void execute(LocalTime overtimeValue, LocalTime latetimeValue, String collaboratorId) {
    Array<HourBankTransaction> transactions = Array.createAsEmpty();
    var overtime = Time.create(overtimeValue);
    var latetime = Time.create(latetimeValue);

    if (overtime.isZero().isFalse()) {
      var creditTransaction = HourBankTransaction.create(overtime.value(), "CREDIT");
      transactions.add(creditTransaction);
    }

    if (latetime.isZero().isFalse()) {
      var debitTransaction = HourBankTransaction.create(latetime.value(), "DEBIT");
      transactions.add(debitTransaction);
    }

    repository.addMany(transactions, Id.create(collaboratorId));
  }
}
