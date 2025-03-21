package br.com.chronos.core.modules.global.domain.exceptions;
public class NotPermitException extends AppException{
  public NotPermitException(String message){
    super("Permit exception",message);
  }
}
