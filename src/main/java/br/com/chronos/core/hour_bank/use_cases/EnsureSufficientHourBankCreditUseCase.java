package br.com.chronos.core.hour_bank.use_cases;

import java.time.Duration;

import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.TimeInterval;
import br.com.chronos.core.hour_bank.domain.exceptions.InsufficientHourBankBalanceException;
import br.com.chronos.core.hour_bank.domain.exceptions.NegativeHourBankException;
import br.com.chronos.core.hour_bank.domain.records.HourBankBalance;
import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;

public class EnsureSufficientHourBankCreditUseCase {
  private final HourBankTransactionsRepository repository;

  public EnsureSufficientHourBankCreditUseCase(HourBankTransactionsRepository repository) {
    this.repository = repository;
  }

  public void execute(String collaboratorId, Duration hourBankCredit) {
    var transactions = repository.findAllByCollaborator(Id.create(collaboratorId));
    var balance = HourBankBalance.create(transactions);

    if (balance.isNegative().isTrue()) {
      throw new NegativeHourBankException();
    }

    System.out.println(balance.value());
    if (balance.value().isLessThan(TimeInterval.create(hourBankCredit)).isTrue()) {
      throw new InsufficientHourBankBalanceException();
    }
  }

}
