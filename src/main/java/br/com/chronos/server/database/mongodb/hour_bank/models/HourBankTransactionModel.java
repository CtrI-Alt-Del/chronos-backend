package br.com.chronos.server.database.mongodb.hour_bank.models;


import java.time.LocalDate;
import java.time.LocalTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import br.com.chronos.core.hour_bank.domain.records.HourBankTransaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "hour_bank_transactions")
@Builder
public class HourBankTransactionModel {
    @Id
    private ObjectId id;
    @Field("operation")
    private HourBankTransaction.Operation operation;
    @Field("reason")
    private HourBankTransaction.Reason reason;
    @Field("date")
    private LocalDate date;
    @Field("time")
    private LocalTime time;
    @Field("collaborator_id")
    private ObjectId collaboratorId;
}