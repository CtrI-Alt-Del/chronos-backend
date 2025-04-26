package br.com.chronos.server.database.mongodb.hour_bank.mappers;

import org.springframework.stereotype.Component;

import br.com.chronos.core.hour_bank.domain.dtos.HourBankTransactionDto;
import br.com.chronos.core.hour_bank.domain.records.HourBankTransaction;
import br.com.chronos.server.database.mongodb.hour_bank.models.HourBankTransactionModel;

@Component
public class HourBankTransactionMapper {
  public HourBankTransactionModel toModel(HourBankTransaction record) {
    var model = HourBankTransactionModel
        .builder()
        .operation(record.operation().name())
        .reason(record.reason().name())
        .time(record.time().value())
        .dateTime(record.dateTime().value())
        .build();
    return model;
  }

  public HourBankTransaction toRecord(HourBankTransactionModel model) {
    var dto = new HourBankTransactionDto()
        .setOperation(model.getOperation().toString())
        .setReason(model.getReason().toString())
        .setDateTime(model.getDateTime())
        .setTime(model.getTime());
    var record = HourBankTransaction.create(dto);
    return record;
  }
}