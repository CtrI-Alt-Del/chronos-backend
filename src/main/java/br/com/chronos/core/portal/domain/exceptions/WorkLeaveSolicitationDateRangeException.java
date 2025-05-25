package br.com.chronos.core.portal.domain.exceptions;

import br.com.chronos.core.global.domain.exceptions.ConflictException;

public class WorkLeaveSolicitationDateRangeException extends ConflictException {
  public WorkLeaveSolicitationDateRangeException(Boolean isVacation) {
    super(isVacation
        ? "Solicitação de férias já existe para o período informado"
        : "Solicitação de afastamento já existe para o período informado");
  }
}
