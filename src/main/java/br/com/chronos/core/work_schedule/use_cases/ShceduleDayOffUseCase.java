package br.com.chronos.core.work_schedule.use_cases;

import java.time.LocalDate;

import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.work_schedule.domain.events.DayOffScheduledEvent;
import br.com.chronos.core.work_schedule.domain.records.Workload;
import br.com.chronos.core.work_schedule.interfaces.WorkScheduleBroker;
import br.com.chronos.core.work_schedule.interfaces.repositories.DayOffSchedulesRepository;

public class ShceduleDayOffUseCase {
  private final DayOffSchedulesRepository repository;
  private final WorkScheduleBroker broker;

  public ShceduleDayOffUseCase(DayOffSchedulesRepository repository, WorkScheduleBroker broker) {
    this.repository = repository;
    this.broker = broker;
  }

  public void execute(String collaboratorId, byte collaboratorWorkload, LocalDate dayOff) {
    var dayOffSchedule = repository.findByCollaborator(Id.create(collaboratorId));
    repository.add(Date.create(dayOff), dayOffSchedule.get().getId());
    var event = new DayOffScheduledEvent(
        Id.create(collaboratorId),
        Workload.create(collaboratorWorkload),
        Date.create(dayOff));
    broker.publish(event);
  }
}
