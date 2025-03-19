package br.com.chronos.core.modules.collaboration.domain.exceptions;

public class ExistingEmailException extends RuntimeException {
  public ExistingEmailException(){
    super("There is already an user with this email");
  }
}
