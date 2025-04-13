package br.com.chronos.server.database.mongodb.hour_bank.mappers;

import br.com.chronos.core.hour_bank.domain.dtos.HourBankTransactionDto;
import br.com.chronos.server.database.mongodb.hour_bank.models.HourBankTransactionModel;

public class HourBankTransactionMapper {   //HourBankTransactionEntity entity
    public HourBankTransactionModel toModel(HourBankTransaction entity) {
        var model = new HourBankTransactionModel()
                .setId(entity.getId())
                .setOperation(entity.getOperation())
                .setReason(entity.getReason())
                .setOperated_at(entity.getOperatedAt());

        return model;
    }
    
}
