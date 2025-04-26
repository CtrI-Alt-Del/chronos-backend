package br.com.chronos.core.hour_bank.domain.records;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.global.domain.records.Time;
import br.com.chronos.core.hour_bank.domain.dtos.HourBankBalanceDto;
import br.com.chronos.core.hour_bank.domain.exceptions.InsufficientHourBankBalanceException;
import br.com.chronos.core.hour_bank.domain.exceptions.NegativeHourBankException;

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

  public Logical hasSufficient(Time time) {
    if (this.isNegative.isTrue()) {
      throw new NegativeHourBankException();
    }

    if (this.value.isLessThan(time).isTrue()) {
      throw new InsufficientHourBankBalanceException();
    }

    return Logical.createAsTrue();
  }

  public HourBankBalance deduct(Time time) {
    hasSufficient(time);

    Time newBalance = this.value.minus(time);
    return new HourBankBalance(newBalance, Logical.createAsFalse());
  }

  public HourBankBalanceDto getDto() {
    return new HourBankBalanceDto()
        .setValue(value.value())
        .setNegative(isNegative.value());
  }

}
