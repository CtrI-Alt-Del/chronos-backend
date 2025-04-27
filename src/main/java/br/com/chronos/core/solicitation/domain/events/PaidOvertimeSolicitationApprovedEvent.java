package br.com.chronos.core.solicitation.domain.events;

import br.com.chronos.core.global.domain.abstracts.Event;
import br.com.chronos.core.solicitation.domain.entities.PaidOvertimeSolicitation;

public class PaidOvertimeSolicitationApprovedEvent
    extends Event<PaidOvertimeSolicitationApprovedEvent.Payload> {
  public static final String NAME = "portal/paid.overtime.solicitation.approved";

  public static record Payload(String collaboratorId) {
  }

  public PaidOvertimeSolicitationApprovedEvent(PaidOvertimeSolicitation solicitation) {
    super(new Payload(solicitation.getSenderResponsible().getId().toString()));
  }
}