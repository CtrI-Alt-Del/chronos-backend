package br.com.chronos.core.work_schedule.use_cases;

import java.time.LocalDate;
import java.time.LocalTime;

import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.Time;
import br.com.chronos.core.work_schedule.domain.entities.WorkdayLog;
import br.com.chronos.core.work_schedule.domain.events.WorkdayClosedEvent;
import br.com.chronos.core.work_schedule.domain.exceptions.WorkdayLogNotFoundException;
import br.com.chronos.core.work_schedule.domain.records.TimePunchPeriod;
import br.com.chronos.core.work_schedule.interfaces.WorkScheduleBroker;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;

public class AdjustTimePunchUseCase {
  private final WorkdayLogsRepository repository;
  private final WorkScheduleBroker broker;

  public AdjustTimePunchUseCase(
      WorkdayLogsRepository repository,
      WorkScheduleBroker broker) {
    this.repository = repository;
    this.broker = broker;
  }

  public void execute(String collaboratorId, LocalDate date, LocalTime time, String period) {
    var workdayLog = findWorkdayLog(Id.create(collaboratorId), Date.create(date));
    workdayLog.getTimePunch().adjust(Time.create(time), TimePunchPeriod.create(period));
    repository.replace(workdayLog);

    var event = new WorkdayClosedEvent(workdayLog);
    broker.publish(event);
  }

  private WorkdayLog findWorkdayLog(Id collaboratorId, Date date) {
    var workdayLog = repository.findByCollaboratorAndDate(collaboratorId, date);
    if (workdayLog.isEmpty()) {
      throw new WorkdayLogNotFoundException();
    }
    return workdayLog.get();
  }
}
