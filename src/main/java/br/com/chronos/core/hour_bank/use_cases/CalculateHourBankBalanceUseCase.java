package br.com.chronos.core.hour_bank.use_cases;

import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.hour_bank.domain.dtos.HourBankBalanceDto;
import br.com.chronos.core.hour_bank.domain.records.HourBankBalance;
import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;

public class CalculateHourBankBalanceUseCase {
  private final HourBankTransactionsRepository repository;

  public CalculateHourBankBalanceUseCase(HourBankTransactionsRepository repository) {
    this.repository = repository;
  }

  public HourBankBalanceDto execute(String collaboratorId) {
    var transactions = repository.findAllByCollaborator(Id.create(collaboratorId));
    var balance = HourBankBalance.create(transactions);
    return balance.getDto();
  }
}
