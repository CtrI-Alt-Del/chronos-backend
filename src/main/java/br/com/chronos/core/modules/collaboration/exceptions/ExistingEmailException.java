package br.com.chronos.core.modules.collaboration.exceptions;

public class ExistingEmailException extends RuntimeException {
  public ExistingEmailException(){
    super("There is already an user with this email");
  }
}
