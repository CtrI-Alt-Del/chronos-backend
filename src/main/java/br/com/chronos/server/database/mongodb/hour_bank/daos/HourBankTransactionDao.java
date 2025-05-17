package br.com.chronos.server.database.mongodb.hour_bank.daos;

import java.util.List;
import java.time.LocalDateTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;

import br.com.chronos.core.hour_bank.domain.records.HourBankTransactionOperation;
import br.com.chronos.server.database.mongodb.hour_bank.models.HourBankTransactionModel;

public interface HourBankTransactionDao extends MongoRepository<HourBankTransactionModel, ObjectId> {
  List<HourBankTransactionModel> findAllByCollaboratorId(String collaboratorId);

  Page<HourBankTransactionModel> findAllByCollaboratorIdAndDateTimeBetweenOrderByDateTimeDesc(
      String collaboratorId,
      LocalDateTime startDate,
      LocalDateTime endDate,
      PageRequest pageRequest);

  Page<HourBankTransactionModel> findAllByCollaboratorIdAndDateTimeBetweenAndOperationOrderByDateTimeDesc(
      String collaboratorId,
      LocalDateTime startDate,
      LocalDateTime endDate,
      HourBankTransactionOperation.OperationName operation,
      PageRequest pageRequest);

  void deleteAllByCollaboratorId(String collaboratorId);
}
