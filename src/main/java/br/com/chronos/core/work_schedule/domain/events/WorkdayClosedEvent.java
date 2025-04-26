package br.com.chronos.core.work_schedule.domain.events;

import java.time.LocalDateTime;
import java.time.LocalTime;

import br.com.chronos.core.global.domain.abstracts.Event;
import br.com.chronos.core.work_schedule.domain.entities.WorkdayLog;

public class WorkdayClosedEvent extends Event<WorkdayClosedEvent.Payload> {
  public static final String KEY = "work.schedule/workday.closed";

  public static record Payload(
      LocalTime overtime,
      LocalTime undertime,
      LocalTime latetime,
      LocalDateTime date,
      String collaboratorId) {

  }

  public WorkdayClosedEvent(WorkdayLog workdayLog) {
    super(new Payload(
        workdayLog.getOvertime().value(),
        workdayLog.getUndertime().value(),
        workdayLog.getLatetime().value(),
        workdayLog.getDate().toDatetime().value(),
        workdayLog.getResponsible().getId().toString()));
  }
}
