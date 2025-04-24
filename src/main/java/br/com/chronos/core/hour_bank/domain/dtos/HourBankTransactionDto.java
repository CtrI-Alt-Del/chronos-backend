package br.com.chronos.core.hour_bank.domain.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

public class HourBankTransactionDto {
  public LocalTime time;
  public LocalDate date;
  public String operation;
  public String reason;

  public HourBankTransactionDto setTime(LocalTime time) {
    this.time = time;
    return this;
  }

  public HourBankTransactionDto setDate(LocalDate date) {
    this.date = date;
    return this;
  }

  public HourBankTransactionDto setOperation(String operation) {
    this.operation = operation;
    return this;
  }

  public HourBankTransactionDto setReason(String reason) {
    this.reason = reason;
    return this;
  }

}
