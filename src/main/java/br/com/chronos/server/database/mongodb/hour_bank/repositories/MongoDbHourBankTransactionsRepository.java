package br.com.chronos.server.database.mongodb.hour_bank.repositories;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.hour_bank.domain.records.HourBankTransaction;
import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;

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
  public Array<HourBankTransaction> findAllByCollaborator(Id collaboratorId) {
    throw new UnsupportedOperationException("Unimplemented method 'findAllByCollaborator'");
  }
}
