package br.com.chronos.server.database.mongodb.hour_bank.models;


import java.time.LocalDate;
import java.util.UUID;

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
    private UUID id;
    @Field("operation")
    private HourBankTransaction operation;
    @Field("reason")
    private String reason; // tipar com string ou o record do reason?
    @Field("operated_at")
    private LocalDate operated_at;
    // possívelmente um relacionamenro com hour_bank
}