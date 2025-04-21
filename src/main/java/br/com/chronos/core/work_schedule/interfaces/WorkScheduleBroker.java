package br.com.chronos.core.work_schedule.interfaces;

import br.com.chronos.core.work_schedule.domain.events.WorkdayClosedEvent;

public interface WorkScheduleBroker {
  public void publish(WorkdayClosedEvent event);
}
