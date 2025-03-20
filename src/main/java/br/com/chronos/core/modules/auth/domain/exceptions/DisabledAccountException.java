package br.com.chronos.core.modules.auth.domain.exceptions;

import br.com.chronos.core.modules.global.domain.exceptions.AppException;

public class DisabledAccountException extends AppException {
  public DisabledAccountException(){
    super("Erro de autenticacao","Conta desativada");
  }
}
