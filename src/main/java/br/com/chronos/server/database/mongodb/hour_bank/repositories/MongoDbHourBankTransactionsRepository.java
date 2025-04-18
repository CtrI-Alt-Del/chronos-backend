package br.com.chronos.server.database.mongodb.hour_bank.repositories;

import java.util.List;
import java.time.LocalDate;
import kotlin.Pair;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
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
import br.com.chronos.server.database.mongodb.hour_bank.mappers.HourBankTransactionMapper;
import br.com.chronos.server.database.mongodb.hour_bank.models.HourBankTransactionModel;

interface SpringDataHourBankRepositoy extends MongoRepository<HourBankTransactionModel, ObjectId> {
  List<HourBankTransactionModel> findAllByCollaboratorId(String collaboratorId);

  Page<HourBankTransactionModel> findAllByCollaboratorIdAndDateBetween(
      String collaboratorId,
      LocalDate startDate,
      LocalDate endDate,
      PageRequest pageRequest);

  Page<HourBankTransactionModel> findAllByCollaboratorIdAndDateBetweenAndOperation(
      String collaboratorId,
      LocalDate startDate,
      LocalDate endDate,
      HourBankTransactionOperation.OperationName operation,
      PageRequest pageRequest);
}

public class MongoDbHourBankTransactionsRepository implements HourBankTransactionsRepository {
  @Autowired
  private SpringDataHourBankRepositoy repository;

  @Autowired
  private HourBankTransactionMapper mapper;

  @Override
  public Array<HourBankTransaction> findAllByCollaborator(Id collaboratorId) {
    var models = repository.findAllByCollaboratorId(collaboratorId.toString());
    return Array.createFrom(models, mapper::toRecord);
  }

  @Override
  public Pair<Array<HourBankTransaction>, PlusIntegerNumber> findManyByCollaboratorDateRageAndOperation(
      Id collaboratorId,
      DateRange dateRange,
      HourBankTransactionOperation operation,
      PageNumber page) {
    var pageRequest = PageRequest.of(page.number().value() - 1, 10);
    var transactionModels = repository.findAllByCollaboratorIdAndDateBetweenAndOperation(
        collaboratorId.toString(),
        dateRange.startDate().value(),
        dateRange.endDate().value(),
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
    var transactionModels = repository.findAllByCollaboratorIdAndDateBetween(
        collaboratorId.toString(),
        dateRange.startDate().value(),
        dateRange.endDate().value(),
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
    repository.save(model);
  }

  @Override
  @Transactional
  public void addMany(Array<HourBankTransaction> transactions, Id collaboratorId) {
    var models = transactions.map((transaction) -> {
      var model = mapper.toModel(transaction);
      model.setCollaboratorId(collaboratorId.toString());
      return model;
    });
    repository.saveAll(models.list());
  }

}
