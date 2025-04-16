package br.com.chronos.core.hour_bank.domain.dtos;

import java.time.LocalTime;

public class HourBankTransactionDto {
  public LocalTime value;
  public String operation;

  public HourBankTransactionDto setValue(LocalTime value) {
    this.value = value;
    return this;
  }

  public HourBankTransactionDto setOperation(String operation) {
    this.operation = operation;
    return this;
  }

}
