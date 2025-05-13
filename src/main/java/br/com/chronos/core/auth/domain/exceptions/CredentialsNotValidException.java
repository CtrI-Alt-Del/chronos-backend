package br.com.chronos.core.auth.domain.exceptions;

import br.com.chronos.core.global.domain.exceptions.AppException;

public class CredentialsNotValidException extends AppException {
  public CredentialsNotValidException() {
    super("Credentials Not Valid Exception", "Credenciais inválidas");
  }
}
