package br.com.chronos.server.database.mongodb.hour_bank.repositories;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.DateRange;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.PageNumber;
import br.com.chronos.core.global.domain.records.PlusIntegerNumber;
import br.com.chronos.core.hour_bank.domain.records.HourBankTransaction;
import br.com.chronos.core.hour_bank.domain.records.HourBankTransactionOperation;
import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;
import kotlin.Pair;

public class MongoDbHourBankTransactionsRepository implements HourBankTransactionsRepository {
  @Override
  public void add(HourBankTransaction transaction) {
    throw new UnsupportedOperationException("Unimplemented method 'add'");
  }

  @Override
  public void addMany(Array<HourBankTransaction> transactions, Id collaboratorId) {
    throw new UnsupportedOperationException("Unimplemented method 'addMany'");
  }

  @Override
  public Pair<Array<HourBankTransaction>, PlusIntegerNumber> findManyByCollaborator(Id collaboratorId,
      DateRange dateRange, HourBankTransactionOperation operation, PageNumber pageNumber) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findManyByCollaborator'");
  }
}
