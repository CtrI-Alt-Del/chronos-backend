package br.com.chronos.core.hour_bank.domain.records;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.Time;
import br.com.chronos.core.hour_bank.domain.dtos.HourBankSummaryDto;

public record HourBankSummary(Time balanceTime, Time creditTime, Time debitTime) {

  public HourBankSummary create(Array<HourBankTransaction> transactions) {
    var balanceTime = Time.createAsZero();
    var creditTime = Time.createAsZero();
    var debitTime = Time.createAsZero();

    for (HourBankTransaction transaction : transactions.list()) {
      if (transaction.isCreditOperation().isTrue()) {
        debitTime = debitTime.plus(transaction.time());
      } else {
        debitTime = debitTime.plus(transaction.time());
      }
    }

    balanceTime = balanceTime.plus(creditTime).minus(debitTime);

    return new HourBankSummary(balanceTime, creditTime, debitTime);
  }

  public HourBankSummaryDto getDto() {
    return new HourBankSummaryDto()
        .setBalance(balanceTime.value())
        .setCredit(creditTime.value())
        .setDebit(debitTime.value());
  }
}
