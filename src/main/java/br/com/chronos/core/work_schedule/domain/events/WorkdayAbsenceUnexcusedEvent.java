package br.com.chronos.core.work_schedule.domain.events;

import java.time.LocalDate;

import br.com.chronos.core.global.domain.abstracts.Event;
import br.com.chronos.core.work_schedule.domain.entities.WorkdayLog;

public class WorkdayAbsenceUnexcusedEvent extends Event<WorkdayAbsenceUnexcusedEvent.Payload> {
  public static final String NAME = "work.schedule/workday.absence.unexcused";

  public static record Payload(String collaboratorEmail, String collaboratorName, LocalDate date) {
  }

  public WorkdayAbsenceUnexcusedEvent(WorkdayLog workdayLog) {
    super(new Payload(
        workdayLog.getResponsible().getEntity().getEmail().value(),
        workdayLog.getResponsible().getEntity().getName().value(),
        workdayLog.getDate().value()));
  }
}
