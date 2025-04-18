package br.com.chronos.core.hour_bank.domain.records;

import br.com.chronos.core.global.domain.exceptions.ValidationException;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.global.domain.records.Text;

public record HourBankTransactionOperation(OperationName name) {
  public static enum OperationName {
    CREDIT,
    DEBIT
  }

  public static HourBankTransactionOperation create(String operation) {
    var text = Text.create(operation.toUpperCase(), "operação do banco de horas");
    try {
      var name = OperationName.valueOf(text.value());
      return new HourBankTransactionOperation(name);
    } catch (Exception e) {
      throw new ValidationException(text.key(), "deve ser crédito ou débito");
    }
  }

  public Logical isCreditOperation() {
    return Logical.create(name.equals(OperationName.CREDIT));
  }

  public Logical isDebitOperation() {
    return Logical.create(name.equals(OperationName.DEBIT));
  }

  public String toString() {
    return name.toString().toLowerCase();
  }
}
