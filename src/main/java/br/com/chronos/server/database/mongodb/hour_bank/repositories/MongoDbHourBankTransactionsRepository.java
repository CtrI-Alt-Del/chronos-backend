package br.com.chronos.server.database.mongodb.hour_bank.repositories;

import kotlin.Pair;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.DateRange;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.PageNumber;
import br.com.chronos.core.global.domain.records.PlusIntegerNumber;
import br.com.chronos.core.hour_bank.domain.records.HourBankTransaction;
import br.com.chronos.core.hour_bank.domain.records.HourBankTransactionOperation;
import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;
import br.com.chronos.server.database.mongodb.hour_bank.daos.HourBankTransactionDao;
import br.com.chronos.server.database.mongodb.hour_bank.mappers.HourBankTransactionMapper;

public class MongoDbHourBankTransactionsRepository implements HourBankTransactionsRepository {
  @Autowired
  private HourBankTransactionDao dao;

  @Autowired
  private HourBankTransactionMapper mapper;

  @Override
  public Array<HourBankTransaction> findAllByCollaborator(Id collaboratorId) {
    var models = dao.findAllByCollaboratorId(collaboratorId.toString());
    return Array.createFrom(models, mapper::toRecord);
  }

  @Override
  public Pair<Array<HourBankTransaction>, PlusIntegerNumber> findManyByCollaboratorDateRageAndOperation(
      Id collaboratorId,
      DateRange dateRange,
      HourBankTransactionOperation operation,
      PageNumber page) {
    var pageRequest = PageRequest.of(page.number().value() - 1, 10);
    var transactionModels = dao.findAllByCollaboratorIdAndDateTimeBetweenAndOperationOrderByDateTimeDesc(
        collaboratorId.toString(),
        dateRange.startDate().minusDays(1).toDatetime().value(),
        dateRange.endDate().plusDays(1).toDatetime().value(),
        operation.name(),
        pageRequest);

    var items = transactionModels.stream().toList();
    var itemsCount = transactionModels.getTotalElements();
    return new Pair<>(
        Array.createFrom(items, mapper::toRecord),
        PlusIntegerNumber.create((int) itemsCount));
  }

  @Override
  public Pair<Array<HourBankTransaction>, PlusIntegerNumber> findManyByCollaboratorAndDateRage(
      Id collaboratorId,
      DateRange dateRange,
      PageNumber page) {
    var pageRequest = PageRequest.of(page.number().value() - 1, 10);
    var transactionModels = dao.findAllByCollaboratorIdAndDateTimeBetweenOrderByDateTimeDesc(
        collaboratorId.toString(),
        dateRange.startDate().minusDays(1).toDatetime().value(),
        dateRange.endDate().plusDays(1).toDatetime().value(),
        pageRequest);
    var items = transactionModels.stream().toList();
    var itemsCount = transactionModels.getTotalElements();
    return new Pair<>(
        Array.createFrom(items, mapper::toRecord),
        PlusIntegerNumber.create((int) itemsCount));
  }

  @Override
  public void add(HourBankTransaction transaction, Id collaboratorId) {
    var model = mapper.toModel(transaction);
    model.setCollaboratorId(collaboratorId.toString());
    dao.save(model);
  }

  @Override
  @Transactional
  public void addMany(Array<HourBankTransaction> transactions, Id collaboratorId) {
    var models = transactions.map((transaction) -> {
      var model = mapper.toModel(transaction);
      model.setCollaboratorId(collaboratorId.toString());
      return model;
    });
    dao.saveAll(models.list());
  }

}
