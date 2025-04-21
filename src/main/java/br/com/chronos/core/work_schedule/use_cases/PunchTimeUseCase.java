package br.com.chronos.core.work_schedule.use_cases;

import java.time.LocalTime;

import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.Time;
import br.com.chronos.core.work_schedule.domain.dtos.TimePunchDto;
import br.com.chronos.core.work_schedule.domain.entities.WorkdayLog;
import br.com.chronos.core.work_schedule.domain.events.WorkdayCompletedEvent;
import br.com.chronos.core.work_schedule.domain.exceptions.WorkdayLogNotFoundException;
import br.com.chronos.core.work_schedule.interfaces.WorkScheduleBroker;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;

public class PunchTimeUseCase {
  private final WorkdayLogsRepository repository;
  private final WorkScheduleBroker broker;

  public PunchTimeUseCase(WorkdayLogsRepository workdayLogsRepository, WorkScheduleBroker broker) {
    this.repository = workdayLogsRepository;
    this.broker = broker;
  }

  public TimePunchDto execute(String workdayLogId, LocalTime time) {
    var workdayLog = findWorkdayLog(Id.create(workdayLogId));
    workdayLog.getTimePunch().punch(Time.create(time, "tempo do ponto"));
    repository.replace(workdayLog);

    if (workdayLog.getTimePunch().isClosed().isTrue()) {
      var event = new WorkdayCompletedEvent(workdayLog);
      broker.publish(event);
    }

    return workdayLog.getTimePunch().getDto();
  }

  private WorkdayLog findWorkdayLog(Id workdayLogId) {
    var workdayLog = repository.findById(workdayLogId);
    if (workdayLog.isEmpty()) {
      throw new WorkdayLogNotFoundException();
    }
    return workdayLog.get();
  }
}
