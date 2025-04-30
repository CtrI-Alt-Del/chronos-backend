package br.com.chronos.core.work_schedule.domain.events;

import java.time.LocalDate;

import br.com.chronos.core.global.domain.abstracts.Event;
import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.work_schedule.domain.records.Workload;

public class DayOffScheduledEvent extends Event<DayOffScheduledEvent.Payload> {
  public static final String NAME = "work.schedule/workday.absence.excused";

  public static record Payload(String collaboratorId, byte collaboratorWorkload, LocalDate dayOff) {

  }

  public DayOffScheduledEvent(Id collaboratorId, Workload collaboratorWorkload, Date dayOff) {
    super(new Payload(
        collaboratorId.toString(),
        collaboratorWorkload.value(),
        dayOff.value()));
  }
}
