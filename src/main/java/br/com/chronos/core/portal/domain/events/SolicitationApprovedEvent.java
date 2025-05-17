package br.com.chronos.core.portal.domain.events;

import br.com.chronos.core.global.domain.abstracts.Event;
import br.com.chronos.core.portal.domain.abstracts.Solicitation;

public class SolicitationApprovedEvent extends Event<SolicitationApprovedEvent.Payload> {
  public static final String NAME = "portal/solicitation.approved";

  public static record Payload(String employeeEmail, String employeeName, String solicitationType) {
  }

  public SolicitationApprovedEvent(Solicitation solicitation) {
    super(new Payload(
        solicitation.getSenderResponsible().getEntity().getEmail().value(),
        solicitation.getSenderResponsible().getEntity().getName().value(),
        solicitation.getType().toString()));
  }
}
