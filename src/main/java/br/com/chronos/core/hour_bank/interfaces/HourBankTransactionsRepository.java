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
  Array<HourBankTransaction> findAllByCollaborator(Id collaboratorId);

  Pair<Array<HourBankTransaction>, PlusIntegerNumber> findManyByCollaboratorAndDateRage(
      Id collaboratorId,
      DateRange dateRange,
      PageNumber pageNumber);

  Pair<Array<HourBankTransaction>, PlusIntegerNumber> findManyByCollaboratorDateRageAndOperation(
      Id collaboratorId,
      DateRange dateRange,
      HourBankTransactionOperation operation,
      PageNumber pageNumber);

  void add(HourBankTransaction transaction, Id collaboratorId);

  void addMany(Array<HourBankTransaction> transactions, Id collaboratorId);
}
