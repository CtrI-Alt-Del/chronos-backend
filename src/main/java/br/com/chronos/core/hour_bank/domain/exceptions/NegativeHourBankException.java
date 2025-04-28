package br.com.chronos.core.hour_bank.domain.exceptions;

import br.com.chronos.core.global.domain.exceptions.ConflictException;

public class NegativeHourBankException extends ConflictException {
  public NegativeHourBankException() {
    super("O saldo já está negativo. Não é possível deduzir horas.");
  }
}
