package br.com.chronos.core.work_schedule.interfaces.brokers;

import br.com.chronos.core.work_schedule.domain.events.TimePunchClosedEvent;

public interface WorkScheduleBroker {
  public void publish(TimePunchClosedEvent event);
}
