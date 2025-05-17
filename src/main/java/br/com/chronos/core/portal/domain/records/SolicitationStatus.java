package br.com.chronos.core.portal.domain.records;

import br.com.chronos.core.global.domain.exceptions.ValidationException;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.global.domain.records.Text;

public record SolicitationStatus(Status value) {
  public enum Status {
    APPROVED,
    PENDING,
    DENIED,
    CANCELED,
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

  public Logical isApproved() {
    return Logical.create(value == Status.APPROVED);
  }

  public Logical isDenied() {
    return Logical.create(value == Status.DENIED);
  }

  public Logical isPending() {
    return Logical.create(value == Status.PENDING);
  }

  public Logical isCanceled() {
    return Logical.create(value == Status.CANCELED);
  }

  public String toString() {
    return value.toString().toLowerCase();
  }

  public SolicitationStatus approve() {
    return new SolicitationStatus(Status.APPROVED);
  }

  public SolicitationStatus cancel() {
    return new SolicitationStatus(Status.CANCELED);
  }
  public SolicitationStatus deny() {
    return new SolicitationStatus(Status.DENIED);
  }
}
