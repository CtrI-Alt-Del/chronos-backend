package br.com.chronos.core.hour_bank.domain.records;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.IntegerNumber;
import br.com.chronos.core.hour_bank.domain.dtos.HourBankSummaryDto;

public record HourBankSummary(
    IntegerNumber balance,
    IntegerNumber credit,
    IntegerNumber debit) {

  public HourBankSummary create(Array<HourBankTransaction> transactions) {
    var balance = IntegerNumber.create(0, "saldo do banco de horas");
    var credit = IntegerNumber.create(0, "crédito do banco de horas");
    var debit = IntegerNumber.create(0, "débito do banco de horas");

    for (HourBankTransaction transaction : transactions.list()) {
      if (transaction.isCreditOperation().isTrue()) {
        credit = credit.plus(transaction.value());
      } else {
        debit = debit.plus(transaction.value());
      }
    }

    balance = balance.plus(credit).minus(debit);

    return new HourBankSummary(balance, credit, debit);
  }

  public HourBankSummaryDto getDto() {
    return new HourBankSummaryDto()
        .setBalance(balance.value())
        .setCredit(credit.value())
        .setDebit(debit.value());
  }
}
