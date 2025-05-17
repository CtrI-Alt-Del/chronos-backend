package br.com.chronos.core.hour_bank.domain.records;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.global.domain.records.TimeInterval;
import br.com.chronos.core.hour_bank.domain.dtos.HourBankBalanceDto;
import br.com.chronos.core.hour_bank.domain.exceptions.InsufficientHourBankBalanceException;
import br.com.chronos.core.hour_bank.domain.exceptions.NegativeHourBankException;

public record HourBankBalance(TimeInterval value, Logical isNegative) {
  public static HourBankBalance create(Array<HourBankTransaction> transactions) {
    var creditTime = TimeInterval.create();
    var debitTime = TimeInterval.create();

    for (HourBankTransaction transaction : transactions.list()) {
      if (transaction.operation().isCreditOperation().isTrue()) {
        creditTime = creditTime.plus(transaction.time());
      } else {
        debitTime = debitTime.plus(transaction.time());
      }
    }

    if (debitTime.isGreaterThan(creditTime).isTrue()) {
      return new HourBankBalance(
          debitTime.minus(creditTime),
          Logical.createAsTrue());
    }

    return new HourBankBalance(
        creditTime.minus(debitTime),
        Logical.createAsFalse());
  }

  public Logical hasSufficient(TimeInterval timeInterval) {
    if (this.isNegative.isTrue()) {
      throw new NegativeHourBankException();
    }

    if (this.value.isLessThan(timeInterval).isTrue()) {
      throw new InsufficientHourBankBalanceException();
    }

    return Logical.createAsTrue();
  }

  public HourBankBalance deduct(TimeInterval timeInterval) {
    hasSufficient(timeInterval);

    var newBalance = value.minus(timeInterval);
    return new HourBankBalance(newBalance, Logical.createAsFalse());
  }

  public HourBankBalanceDto getDto() {
    return new HourBankBalanceDto()
        .setValue(value.getDto())
        .setNegative(isNegative.value());
  }

}
