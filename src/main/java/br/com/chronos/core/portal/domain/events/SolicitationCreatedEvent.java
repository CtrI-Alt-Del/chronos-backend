package br.com.chronos.core.portal.domain.events;

import br.com.chronos.core.global.domain.abstracts.Event;
import br.com.chronos.core.portal.domain.abstracts.Solicitation;

public class SolicitationCreatedEvent extends Event<SolicitationCreatedEvent.Payload> {
  public static final String NAME = "portal/solicitation.created";

  public static record Payload(String collaboratorationSector, String solicitationType) {
  }

  public SolicitationCreatedEvent(Solicitation solicitation) {
    super(new Payload(
        solicitation.getSenderResponsible().getEntity().getSector().toString(),
        solicitation.getType().toString()));
  }
}
