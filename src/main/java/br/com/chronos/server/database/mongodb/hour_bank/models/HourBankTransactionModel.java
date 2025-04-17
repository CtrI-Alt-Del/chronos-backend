package br.com.chronos.server.database.mongodb.hour_bank.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import br.com.chronos.core.hour_bank.domain.records.HourBankTransactionOperation;
import br.com.chronos.core.hour_bank.domain.records.HourBankTransactionReason;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document(collection = "hour_bank_transactions")
public class HourBankTransactionModel {
    @Id
    private ObjectId id;
    @Field
    private HourBankTransactionOperation.OperationName operation;
    @Field
    private HourBankTransactionReason.ReasonName reason;
    @Field
    private LocalDate date;
    @Field
    private LocalTime time;
    @Field
    private String collaboratorId;
}