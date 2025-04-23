package br.com.chronos.core.hour_bank.domain.records;

import br.com.chronos.core.global.domain.exceptions.ValidationException;
import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.global.domain.records.Time;
import br.com.chronos.core.hour_bank.domain.dtos.HourBankBalanceDto;

public record HourBankBalance(Time value, Logical isNegative) {

  public static HourBankBalance create(Array<HourBankTransaction> transactions) {
    var creditTime = Time.createAsZero();
    var debitTime = Time.createAsZero();

    for (HourBankTransaction transaction : transactions.list()) {
      if (transaction.operation().isCreditOperation().isTrue()) {
        creditTime = creditTime.plus(transaction.time());
      } else {
        debitTime = debitTime.plus(transaction.time());
      }
    }

    if (debitTime.isGreaterThan(creditTime).isTrue()) {
      return new HourBankBalance(
          debitTime.getDifferenceFrom(creditTime),
          Logical.createAsTrue());
    }

    return new HourBankBalance(
        creditTime.minus(debitTime),
        Logical.createAsFalse());
  }

  public HourBankBalanceDto getDto() {
    return new HourBankBalanceDto()
        .setValue(value.value())
        .setNegative(isNegative.value());
  }

  public HourBankBalance deduct(int workloadHours) {
    Time workload = Time.create(workloadHours, 0);
    if (this.isNegative.isTrue()) {
      throw new ValidationException("Banco de horas", "O saldo já está negativo. Não é possível deduzir horas.");
    }

    if (this.value.isLessThan(workload).isTrue()) {
      throw new ValidationException("Banco de horas", "Saldo insuficiente no banco de horas.");
    }

    Time newBalance = this.value.minus(workload);
    return new HourBankBalance(newBalance, Logical.createAsFalse());
  }
}
