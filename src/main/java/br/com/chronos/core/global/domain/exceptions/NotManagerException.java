package br.com.chronos.core.global.domain.exceptions;

public class NotManagerException extends NotPermitException {
  public NotManagerException() {
    super("Colaborador deve possuir cargo de gerente");
  }
}