package br.com.chronos.core.auth.domain.exceptions;

import br.com.chronos.core.global.domain.exceptions.ConflictException;

public class ToggleSameAccountException extends ConflictException {
  public ToggleSameAccountException() {
    super("Uma conta não pode mudar o status dela mesma");
  }
}
