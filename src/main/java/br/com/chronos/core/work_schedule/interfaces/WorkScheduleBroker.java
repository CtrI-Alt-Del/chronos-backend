package br.com.chronos.core.work_schedule.interfaces;

import br.com.chronos.core.work_schedule.domain.events.WorkdayAbsenceExcusedEvent;
import br.com.chronos.core.work_schedule.domain.events.WorkdayClosedEvent;
import br.com.chronos.core.work_schedule.domain.events.WorkdayAbsenceUnexcusedEvent;

public interface WorkScheduleBroker {
  public void publish(WorkdayClosedEvent event);

  public void publish(WorkdayAbsenceExcusedEvent event);

  public void publish(WorkdayAbsenceUnexcusedEvent event);
}
