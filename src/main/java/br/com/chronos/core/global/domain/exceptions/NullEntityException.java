package br.com.chronos.core.global.domain.exceptions;

public class NullEntityException extends AppException {
  public NullEntityException(String entityName) {
    super("Entidade " + entityName + " é nula");
  }
}
