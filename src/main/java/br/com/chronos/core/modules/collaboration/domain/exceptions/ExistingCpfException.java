package br.com.chronos.core.modules.collaboration.domain.exceptions;

import br.com.chronos.core.modules.global.domain.exceptions.ConflictException;

public class ExistingCpfException extends ConflictException{
  public  ExistingCpfException(){
    super("There is already an user with this cpf");

  }
}
