package br.com.chronos.core.work_schedule.domain.events;

import java.time.LocalTime;

import br.com.chronos.core.global.domain.abstracts.Event;
import br.com.chronos.core.work_schedule.domain.entities.WorkdayLog;

public class WorkdayAbsenceExcusedEvent extends Event<WorkdayAbsenceExcusedEvent.Payload> {
  public static final String NAME = "work.schedule/workday.absence.excused";

  public static record Payload(String collaboratorId, LocalTime hourBankDebit) {

  }

  public WorkdayAbsenceExcusedEvent(WorkdayLog workdayLog) {
    super(new Payload(
        workdayLog.getResponsible().getId().toString(),
        workdayLog.getHourBankDebit().value()));
  }
}
