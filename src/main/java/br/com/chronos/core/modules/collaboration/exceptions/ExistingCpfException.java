package br.com.chronos.core.modules.collaboration.exceptions;

public class ExistingCpfException extends RuntimeException{
  public  ExistingCpfException(){
    super("There is already an user with this cpf");

  }
}
