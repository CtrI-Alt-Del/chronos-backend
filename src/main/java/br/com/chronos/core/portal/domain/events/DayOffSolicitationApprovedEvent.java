package br.com.chronos.core.portal.domain.events;

import java.time.LocalDate;

import br.com.chronos.core.global.domain.abstracts.Event;
import br.com.chronos.core.portal.domain.entities.DayOffSolicitation;

public class DayOffSolicitationApprovedEvent extends Event<DayOffSolicitationApprovedEvent.Payload> {
  public static final String NAME = "portal/day.off.solicitation.approved";

  public static record Payload(String collaboratorId, int collaboratorWorkload, LocalDate dayOff) {
  }

  public DayOffSolicitationApprovedEvent(DayOffSolicitation solicitation) {
    super(new Payload(
        solicitation.getSenderResponsible().getId().toString(),
        solicitation.getWorkload().value(),
        solicitation.getDayOff().value()));
  }
}
