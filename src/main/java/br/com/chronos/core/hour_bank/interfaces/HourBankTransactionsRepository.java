package br.com.chronos.core.hour_bank.interfaces;

import kotlin.Pair;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.DateRange;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.PageNumber;
import br.com.chronos.core.global.domain.records.PlusIntegerNumber;
import br.com.chronos.core.hour_bank.domain.records.HourBankTransaction;
import br.com.chronos.core.hour_bank.domain.records.HourBankTransactionOperation;

public interface HourBankTransactionsRepository {
  Pair<Array<HourBankTransaction>, PlusIntegerNumber> findManyByCollaborator(
      Id collaboratorId,
      DateRange dateRange,
      HourBankTransactionOperation operation,
      PageNumber pageNumber);

  void add(HourBankTransaction transaction);

  void addMany(Array<HourBankTransaction> transactions, Id collaboratorId);
}
