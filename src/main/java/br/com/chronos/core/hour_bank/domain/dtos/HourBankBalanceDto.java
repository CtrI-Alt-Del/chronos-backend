package br.com.chronos.core.hour_bank.domain.dtos;

public class HourBankBalanceDto {
  public String value;
  public boolean isNegative;

  public HourBankBalanceDto setValue(String value) {
    this.value = value;
    return this;
  }

  public HourBankBalanceDto setNegative(boolean negative) {
    isNegative = negative;
    return this;
  }
}
