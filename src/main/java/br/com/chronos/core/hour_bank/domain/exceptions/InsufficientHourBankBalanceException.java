package br.com.chronos.core.hour_bank.domain.exceptions;

import br.com.chronos.core.global.domain.exceptions.ConflictException;

public class InsufficientHourBankBalanceException extends ConflictException {
  public InsufficientHourBankBalanceException() {
    super("Saldo insuficiente no banco de horas.");
  }
}
