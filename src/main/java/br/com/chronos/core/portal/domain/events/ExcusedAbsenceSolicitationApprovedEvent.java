package br.com.chronos.core.portal.domain.events;

import java.time.LocalDate;

import br.com.chronos.core.global.domain.abstracts.Event;
import br.com.chronos.core.portal.domain.entities.ExcusedAbsenceSolicitation;

public class ExcusedAbsenceSolicitationApprovedEvent
    extends Event<ExcusedAbsenceSolicitationApprovedEvent.Payload> {
  public static final String NAME = "portal/excused.absence.solicitation.approved";

  public static record Payload(String collaboratorId, LocalDate absenceDate) {
  }

  public ExcusedAbsenceSolicitationApprovedEvent(ExcusedAbsenceSolicitation solicitation) {
    super(new Payload(
        solicitation.getSenderResponsible().getId().toString(),
        solicitation.getAbsenceDate().value()));
  }
}
