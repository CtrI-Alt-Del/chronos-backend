package br.com.chronos.core.modules.collaboration.domain.exceptions;

import br.com.chronos.core.modules.global.domain.exceptions.ConflictException;

public class ExistingCpfException extends ConflictException{
  public  ExistingCpfException(){
    super("Ja existe um usuario com esse CPF");

  }
}
