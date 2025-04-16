package br.com.chronos.core.work_schedule.domain.events;

import java.time.LocalTime;

import br.com.chronos.core.global.domain.abstracts.Event;
import br.com.chronos.core.work_schedule.domain.entities.WorkdayLog;

public class TimePunchClosedEvent extends Event<TimePunchClosedEvent.Payload> {
  public static final String KEY = "work.schedule/time.punch.closed";

  public static class Payload {
    public final LocalTime overtime;
    public final LocalTime undertime;
    public final LocalTime latetime;
    public final String collaboratorId;

    public Payload(LocalTime overtime, LocalTime undertime, LocalTime latetime, String collaboratorId) {
      this.overtime = overtime;
      this.undertime = undertime;
      this.latetime = latetime;
      this.collaboratorId = collaboratorId;
    }
  }

  public TimePunchClosedEvent(WorkdayLog workdayLog) {
    super(new Payload(
        workdayLog.getOvertime().value(),
        workdayLog.getUndertime().value(),
        workdayLog.getLatetime().value(),
        workdayLog.getResponsible().getId().toString()));
  }
}
