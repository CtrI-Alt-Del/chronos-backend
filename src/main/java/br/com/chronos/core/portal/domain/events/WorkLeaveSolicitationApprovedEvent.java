package br.com.chronos.core.portal.domain.events;

import java.time.LocalDate;

import br.com.chronos.core.global.domain.abstracts.Event;
import br.com.chronos.core.portal.domain.entities.WorkLeaveSolicitation;

public class WorkLeaveSolicitationApprovedEvent extends Event<WorkLeaveSolicitationApprovedEvent.Payload> {
  public static final String NAME = "portal/work.leave.solicitation.approved";

  public static record Payload(LocalDate startedAt, LocalDate endedAt, boolean isVacation, String collaboratorId) {
  }

  public WorkLeaveSolicitationApprovedEvent(WorkLeaveSolicitation solicitation) {
    super(new Payload(
        solicitation.getStartedAt().value(),
        solicitation.getEndedAt().value(),
        solicitation.getIsVacation().value(),
        solicitation.getSenderResponsible().getId().toString()));
  }

}
