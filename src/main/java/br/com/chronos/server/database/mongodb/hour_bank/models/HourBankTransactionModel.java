package br.com.chronos.server.database.mongodb.hour_bank.models;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.chronos.core.hour_bank.domain.records.HourBankReason;
import br.com.chronos.core.hour_bank.domain.records.HourBankTransaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "hour_bank_transactions")
public class HourBankTransactionModel {
    @Id
    private UUID id;
    private HourBankTransaction operation;
    private HourBankReason reason;
    private LocalDate operated_at;
    // possívelmente um relacionamenro com hour_bank
}