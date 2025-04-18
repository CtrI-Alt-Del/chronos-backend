package br.com.chronos.core.work_schedule.use_cases;

import java.time.LocalTime;

import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.Time;
import br.com.chronos.core.work_schedule.domain.entities.WorkdayLog;
import br.com.chronos.core.work_schedule.domain.events.WorkdayCompletedEvent;
import br.com.chronos.core.work_schedule.domain.exceptions.WorkdayLogNotFoundException;
import br.com.chronos.core.work_schedule.domain.records.TimePunchPeriod;
import br.com.chronos.core.work_schedule.interfaces.WorkScheduleBroker;
import br.com.chronos.core.work_schedule.interfaces.repositories.TimePunchesRepository;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;

public class AdjustTimePunchUseCase {
  private final WorkdayLogsRepository workdayLogsRepository;
  private final TimePunchesRepository timePunchesRepository;
  private final WorkScheduleBroker broker;

  public AdjustTimePunchUseCase(
      TimePunchesRepository timePunchesRepository,
      WorkdayLogsRepository workdayLogsRepository,
      WorkScheduleBroker broker) {
    this.timePunchesRepository = timePunchesRepository;
    this.workdayLogsRepository = workdayLogsRepository;
    this.broker = broker;
  }

  public void execute(String timePunchId, LocalTime time, String period) {
    var workdayLog = findWorkdayLog(Id.create(timePunchId));
    var timePunch = workdayLog.getTimePunch();
    timePunch.adjust(Time.create(time), TimePunchPeriod.create(period));
    timePunchesRepository.replace(timePunch);

    var event = new WorkdayCompletedEvent(workdayLog);
    broker.publish(event);
  }

  private WorkdayLog findWorkdayLog(Id timePunchId) {
    var workdayLog = workdayLogsRepository.findByTimePunch(timePunchId);
    if (workdayLog.isEmpty()) {
      throw new WorkdayLogNotFoundException();
    }
    return workdayLog.get();
  }
}
