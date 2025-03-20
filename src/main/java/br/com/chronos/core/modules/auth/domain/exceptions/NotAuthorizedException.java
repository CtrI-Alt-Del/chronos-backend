package br.com.chronos.core.modules.auth.domain.exceptions;

import br.com.chronos.core.modules.global.domain.exceptions.AppException;

public class NotAuthorizedException extends AppException {
  public NotAuthorizedException(){
    super("Erro de autenticacao","Permissao invalida");
  }
}
