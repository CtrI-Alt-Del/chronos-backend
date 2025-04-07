package br.com.chronos.core.hour_bank.domain.records;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.IntegerNumber;
import br.com.chronos.core.hour_bank.domain.dtos.HourBankSummaryDto;

public record HourBankSummary(
    IntegerNumber balance,
    IntegerNumber credit,
    IntegerNumber debit) {

  public HourBankSummary create(Array<HourBankTransaction> transactions) {
    credit = IntegerNumber.create(dto.credit, "crédito do banco de horas");
    debit = IntegerNumber.create(dto.debit, "débito do banco de horas");
  }

  public HourBankSummaryDto getDto() {
    return new HourBankSummaryDto()
        .setBalance(balance.value())
        .setCredit(credit.value())
        .setDebit(debit.value());
  }
}
