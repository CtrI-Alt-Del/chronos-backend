package br.com.chronos.server.database.mongodb.hour_bank.repositories;


import kotlin.Pair;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.DateRange;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.PageNumber;
import br.com.chronos.core.global.domain.records.PlusIntegerNumber;
import br.com.chronos.core.hour_bank.domain.records.HourBankTransaction;
import br.com.chronos.core.hour_bank.domain.records.HourBankTransactionOperation;
import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;

import br.com.chronos.server.database.mongodb.hour_bank.mappers.HourBankTransactionMapper;
import br.com.chronos.server.database.mongodb.hour_bank.models.HourBankTransactionModel;

import org.springframework.transaction.annotation.Transactional;

interface SpringDataHourBankRepositoy extends MongoRepository<HourBankTransactionModel, ObjectId> {

}

public class MongoDbHourBankTransactionsRepository implements HourBankTransactionsRepository {

@Autowired
  private SpringDataHourBankRepositoy repository;

@Autowired
  private HourBankTransactionMapper mapper;

  @Override
  @Transactional
  public void add(HourBankTransaction transaction) {
    HourBankTransactionModel model = mapper.toModel(transaction);
    repository.save(model);

  }

  @Override
  public void addMany(Array<HourBankTransaction> transactions, Id collaboratorId) {
    throw new UnsupportedOperationException("Unimplemented method 'addMany'");
  }

  @Override
  public Array<HourBankTransaction> findAllByCollaborator(Id collaboratorId) {
    throw new UnsupportedOperationException("Unimplemented method 'findAllByCollaborator'");
  }

  @Override
  public Pair<Array<HourBankTransaction>, PlusIntegerNumber> findManyByCollaboratorDateRageAndOperation(
      Id collaboratorId, DateRange dateRange, HourBankTransactionOperation operation, PageNumber pageNumber) {
    throw new UnsupportedOperationException("Unimplemented method 'findManyByCollaboratorDateRageAndOperation'");
  }

}
