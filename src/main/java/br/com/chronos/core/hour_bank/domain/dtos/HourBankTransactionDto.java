package br.com.chronos.core.hour_bank.domain.dtos;

public class HourBankTransactionDto {
  public int value;
  public String operation;

  public HourBankTransactionDto setValue(int value) {
    this.value = value;
    return this;
  }

  public HourBankTransactionDto setOperation(String operation) {
    this.operation = operation;
    return this;
  }

}
