package br.com.chronos.core.portal.domain.events;

import java.time.LocalDate;
import java.util.List;

import br.com.chronos.core.global.domain.abstracts.Event;
import br.com.chronos.core.portal.domain.entities.DayOffScheduleAdjustmentSolicitation;

public class DayOffScheduleSolicitationApprovedEvent extends Event<DayOffScheduleSolicitationApprovedEvent.Payload> {
  public static final String NAME = "portal/day.off.schedule.solicitation.approved";

  public static record Payload(String collaboratorId, List<LocalDate> daysOff, int dayOffCount, int workDayCount) {

  }

  public DayOffScheduleSolicitationApprovedEvent(DayOffScheduleAdjustmentSolicitation solicitation) {
    super(new Payload(
        solicitation.getSenderResponsible().getId().toString(),
        solicitation.getDayOffSchedule().getDaysOff().map(dayOff -> dayOff.value()).list(),
        solicitation.getDayOffSchedule().getDaysOffCount().integer().value(),
        solicitation.getDayOffSchedule().getWorkdaysCount().integer().value()));
  }
}
