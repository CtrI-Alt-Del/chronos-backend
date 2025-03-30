package br.com.chronos.core.modules.solicitation.domain.records;

import br.com.chronos.core.modules.global.domain.exceptions.ValidationException;
import br.com.chronos.core.modules.global.domain.records.Text;

public record SolicitationStatus(Status value) {
  public enum Status {
    APPROVED,
    PENDING,
    DENIED
  }

  public static SolicitationStatus create(String value) {
    if (value == null) {
      return new SolicitationStatus(Status.PENDING);
      
    }
    var text = Text.create(value.toUpperCase(), "status");
    try {
      return new SolicitationStatus(Status.valueOf(text.value()));
    } catch (Exception e) {
      throw new ValidationException(text.key(), "deve ser aprovado, em andamento ou negado");
    }
  }
}
