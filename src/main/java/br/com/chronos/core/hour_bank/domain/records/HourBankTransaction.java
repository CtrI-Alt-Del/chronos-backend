package br.com.chronos.core.hour_bank.domain.records;

import java.time.LocalDate;
import java.time.LocalTime;

import br.com.chronos.core.global.domain.exceptions.ValidationException;
import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.global.domain.records.Text;
import br.com.chronos.core.global.domain.records.Time;
import br.com.chronos.core.hour_bank.domain.dtos.HourBankTransactionDto;

public record HourBankTransaction(Time time, Operation operation, Reason reason, Date date) {
  public enum Operation {
    CREDIT,
    DEBIT
  }

  public enum Reason {
    ABSENCE,
    OVERTIME,
    LATETIME,
    SYSTEM_ADJUSTMENT,
  }

  public static HourBankTransaction create(LocalTime time, String operation, String reason, LocalDate date) {
    var transactionTime = Time.create(time);
    var transactionDate = Date.create(date);
    var transactionOperation = getOperation(operation);
    var transactionReason = Reason.SYSTEM_ADJUSTMENT;
    return new HourBankTransaction(transactionTime, transactionOperation, transactionReason, transactionDate);
  }

  private static Operation getOperation(String operation) {
    var text = Text.create(operation.toUpperCase(), "operação do banco de horas");
    try {
      return Operation.valueOf(text.value());
    } catch (Exception e) {
      throw new ValidationException(text.key(), "deve ser admin, gestor ou funcionário");
    }
  }

  public Logical isCreditOperation() {
    return Logical.create(operation.equals(Operation.CREDIT));
  }

  public Logical isDebitOperation() {
    return Logical.create(operation.equals(Operation.DEBIT));
  }

  public HourBankTransactionDto getDto() {
    return new HourBankTransactionDto()
        .setValue(time.value())
        .setOperation(operation.name());
  }

}
