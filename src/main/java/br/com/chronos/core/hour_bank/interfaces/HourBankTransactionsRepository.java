package br.com.chronos.core.hour_bank.interfaces;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.hour_bank.domain.records.HourBankTransaction;

public interface HourBankTransactionsRepository {
  void add(HourBankTransaction transaction);

  void addMany(Array<HourBankTransaction> transactions, Id collaboratorId);

  Array<HourBankTransaction> findAllByCollaborator(Id collaboratorId);
}
