package br.com.chronos.core.solicitation.domain.events;

import br.com.chronos.core.global.domain.abstracts.Event;
import br.com.chronos.core.global.domain.aggregates.ResponsibleAggregate;

public class PaidOvertimeSolicitationApprovedEvent
    extends Event<PaidOvertimeSolicitationApprovedEvent.Payload> {
  public static final String NAME = "portal/paid.overtime.solicitation.approved";

  public static record Payload(String collaboratorId) {
  }

  public PaidOvertimeSolicitationApprovedEvent(ResponsibleAggregate senderResponsible) {
    super(new Payload(senderResponsible.getId().toString()));
  }
}