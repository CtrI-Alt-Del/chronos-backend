package br.com.chronos.core.portal.domain.events;

import br.com.chronos.core.global.domain.abstracts.Event;
import br.com.chronos.core.portal.domain.abstracts.Solicitation;

public class SolicitationDeniedEvent extends Event<SolicitationDeniedEvent.Payload> {
  public static final String NAME = "portal/solicitation.denied";

  public static record Payload(String employeeEmail, String employeeName, String solicitationType) {
  }

  public SolicitationDeniedEvent(Solicitation solicitation) {
    super(new Payload(
        solicitation.getSenderResponsible().getEntity().getEmail().value(),
        solicitation.getSenderResponsible().getEntity().getName().value(),
        solicitation.getType().toString()));
  }
}
