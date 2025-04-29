package br.com.chronos.core.hour_bank.use_cases;

import java.time.LocalTime;
import java.util.logging.Logger;

import br.com.chronos.core.global.domain.exceptions.ValidationException;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.Time;
import br.com.chronos.core.hour_bank.domain.exceptions.InsufficientHourBankBalanceException;
import br.com.chronos.core.hour_bank.domain.exceptions.NegativeHourBankException;
import br.com.chronos.core.hour_bank.domain.records.HourBankBalance;
import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;

public class EnsureSufficientHourBankCreditUseCase {
  private final HourBankTransactionsRepository repository;

  public EnsureSufficientHourBankCreditUseCase(HourBankTransactionsRepository repository) {
    this.repository = repository;
  }

  public void execute(String collaboratorId, LocalTime time) {
    var transactions = repository.findAllByCollaborator(Id.create(collaboratorId));
    var balance = HourBankBalance.create(transactions);

    if (time.equals(LocalTime.MIDNIGHT)) {
      throw new ValidationException("carga horaria", "nao pode ser zero");
      
    }

    if (balance.isNegative().isTrue()) {
      throw new NegativeHourBankException();
    }

    System.out.println(balance.value());
    Logger.getAnonymousLogger().info("Balance" + time);
    if (balance.value().isLessThan(Time.create(time)).isTrue()) {
      throw new InsufficientHourBankBalanceException();
    }
  }

}
