package br.com.chronos.core.work_schedule.interfaces;

import br.com.chronos.core.work_schedule.domain.events.WorkdayDoneEvent;

public interface WorkScheduleBroker {
  public void publish(WorkdayDoneEvent event);
}
