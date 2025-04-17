package br.com.chronos.core.hour_bank.domain.dtos;

import java.time.LocalTime;

public class HourBankBalanceDto {
  public LocalTime value;
  public boolean isNegative;

  public HourBankBalanceDto setValue(LocalTime value) {
    this.value = value;
    return this;
  }

  public HourBankBalanceDto setNegative(boolean negative) {
    isNegative = negative;
    return this;
  }
}
