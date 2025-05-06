package br.com.chronos.core.portal.domain.events;

import java.time.LocalDate;
import java.time.LocalTime;

import br.com.chronos.core.global.domain.abstracts.Event;
import br.com.chronos.core.portal.domain.entities.TimePunchAdjustmentSolicitation;

public class TimePunchAdjusmentSolicitationApprovedEvent
    extends Event<TimePunchAdjusmentSolicitationApprovedEvent.Payload> {
  public static final String NAME = "portal/time.punch.solicitation.approved";

  public static record Payload(
      LocalTime time,
      String period,
      LocalDate workdayLogDate) {
  }

  public TimePunchAdjusmentSolicitationApprovedEvent(TimePunchAdjustmentSolicitation solicitation) {
    super(new Payload(
        solicitation.getTime().value(),
        solicitation.getPeriod().toString(),
        solicitation.getWorkdayLogDate().value()));
  }
}
