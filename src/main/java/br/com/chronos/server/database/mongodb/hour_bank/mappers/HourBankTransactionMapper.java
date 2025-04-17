package br.com.chronos.server.database.mongodb.hour_bank.mappers;


import org.springframework.stereotype.Component;

import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.Time;
import br.com.chronos.core.hour_bank.domain.records.HourBankTransaction;
import br.com.chronos.server.database.mongodb.hour_bank.models.HourBankTransactionModel;

@Component
public class HourBankTransactionMapper {
    public HourBankTransactionModel toModel(HourBankTransaction record) {
      var model = HourBankTransactionModel.builder()
        .operation(record.operation())
        .reason(record.reason())
        .time(record.time().value())
        .date(record.date().value())
        .build();
        return model;
          
    }

    public HourBankTransaction toRecord (HourBankTransactionModel model) {
        var record = new HourBankTransaction(
            Time.create(model.getTime()),
            model.getOperation(),
            model.getReason(),
            Date.create(model.getDate())
        );
        return record;
    
    }

}