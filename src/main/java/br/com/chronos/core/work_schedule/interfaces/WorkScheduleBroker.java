package br.com.chronos.core.work_schedule.interfaces;

import br.com.chronos.core.work_schedule.domain.events.WorkdayCompletedEvent;

public interface WorkScheduleBroker {
  public void publish(WorkdayCompletedEvent event);
}
