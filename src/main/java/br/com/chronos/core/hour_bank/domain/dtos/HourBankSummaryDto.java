package br.com.chronos.core.hour_bank.domain.dtos;

import java.time.LocalTime;

public class HourBankSummaryDto {
  public LocalTime balance;
  public LocalTime credit;
  public LocalTime debit;

  public HourBankSummaryDto setBalance(LocalTime balance) {
    this.balance = balance;
    return this;
  }

  public HourBankSummaryDto setCredit(LocalTime credit) {
    this.credit = credit;
    return this;
  }

  public HourBankSummaryDto setDebit(LocalTime debit) {
    this.debit = debit;
    return this;
  }
}
