package br.com.chronos.core.hour_bank.domain.records;

import java.time.LocalTime;

import br.com.chronos.core.global.domain.exceptions.ValidationException;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.global.domain.records.Text;
import br.com.chronos.core.global.domain.records.Time;

public record HourBankTransaction(Time time, Operation operation) {
  public enum Operation {
    CREDIT,
    DEBIT
  }

  public static HourBankTransaction create(LocalTime time, String operation) {
    var transactionTime = Time.create(time);
    var transactionOperation = getOperation(operation);

    return new HourBankTransaction(transactionTime, transactionOperation);
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

}
