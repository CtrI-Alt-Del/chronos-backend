package br.com.chronos.core.hour_bank.domain.records;

import br.com.chronos.core.global.domain.exceptions.ValidationException;
import br.com.chronos.core.global.domain.records.IntegerNumber;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.global.domain.records.Text;
import br.com.chronos.core.hour_bank.domain.dtos.HourBankTransactionDto;

public record HourBankTransaction(IntegerNumber value, Operation operation) {
  public enum Operation {
    CREDIT,
    DEBIT
  }

  public HourBankTransaction create(HourBankTransactionDto dto) {
    var value = IntegerNumber.create(dto.value, "valor da transação do banco de horas");
    var operation = getOperation(dto.operation);

    return new HourBankTransaction(value, operation);
  }

  private Operation getOperation(String operation) {
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
