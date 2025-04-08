package br.com.chronos.core.work_schedule.domain.events;

import java.time.LocalTime;

import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.work_schedule.domain.entities.WorkdayLog;

public class TimePunchClosedEvent {
  public static final String KEY = "work.schedule/time.punch.closed";
  private Payload payload;

  public static class Payload {
    private final LocalTime overtime;
    private final LocalTime latetime;
    private final String collaboratorId;

    public Payload(LocalTime overtime, LocalTime latetime, String collaboratorId) {
      this.overtime = overtime;
      this.latetime = latetime;
      this.collaboratorId = collaboratorId;
    }

    public LocalTime getOvertime() {
      return overtime;
    }

    public LocalTime getLatetime() {
      return latetime;
    }

    public String getCollaboratorId() {
      return collaboratorId;
    }

  }

  public TimePunchClosedEvent(WorkdayLog workdayLog, Id collaboratorId) {
    payload = new Payload(
        workdayLog.getOvertime().value(),
        workdayLog.getLatetime().value(),
        collaboratorId.toString());
  }

  public Payload getPayload() {
    return payload;
  }
}
