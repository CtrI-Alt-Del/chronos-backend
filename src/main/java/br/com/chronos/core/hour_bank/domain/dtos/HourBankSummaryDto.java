package br.com.chronos.core.hour_bank.domain.dtos;

public class HourBankSummaryDto {
  public int balance;
  public int credit;
  public int debit;

  public HourBankSummaryDto setBalance(int balance) {
    this.balance = balance;
    return this;
  }

  public HourBankSummaryDto setCredit(int credit) {
    this.credit = credit;
    return this;
  }

  public HourBankSummaryDto setDebit(int debit) {
    this.debit = debit;
    return this;
  }
}
