package br.com.chronos.core.work_schedule.use_cases;

import java.time.LocalTime;

import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.Time;
import br.com.chronos.core.work_schedule.domain.dtos.TimePunchDto;
import br.com.chronos.core.work_schedule.domain.entities.TimePunch;
import br.com.chronos.core.work_schedule.domain.entities.WorkdayLog;
import br.com.chronos.core.work_schedule.domain.events.WorkdayDoneEvent;
import br.com.chronos.core.work_schedule.domain.exceptions.TimePunchNotFoundException;
import br.com.chronos.core.work_schedule.domain.exceptions.WorkdayLogNotFoundException;
import br.com.chronos.core.work_schedule.interfaces.WorkScheduleBroker;
import br.com.chronos.core.work_schedule.interfaces.repositories.TimePunchesRepository;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;

public class PunchTimeUseCase {
  private final TimePunchesRepository timePunchesRepository;
  private final WorkdayLogsRepository workdayLogsRepository;
  private final WorkScheduleBroker broker;

  public PunchTimeUseCase(
      TimePunchesRepository timePunchesRepository,
      WorkdayLogsRepository workdayLogsRepository,
      WorkScheduleBroker broker) {
    this.timePunchesRepository = timePunchesRepository;
    this.workdayLogsRepository = workdayLogsRepository;
    this.broker = broker;
  }

  public TimePunchDto execute(String timePunchId, LocalTime time) {
    var timePunch = findTimePunch(timePunchId);
    timePunch.punch(Time.create(time, "tempo do ponto"));
    timePunchesRepository.replace(timePunch);

    if (timePunch.isClosed().isTrue()) {
      var workdayLog = findWorkdayLog(timePunch);
      var event = new WorkdayDoneEvent(workdayLog);
      broker.publish(event);
    }

    return timePunch.getDto();
  }

  private TimePunch findTimePunch(String timePunchId) {
    var timePunch = timePunchesRepository.findById(Id.create(timePunchId));
    if (timePunch.isEmpty()) {
      throw new TimePunchNotFoundException();
    }

    return timePunch.get();
  }

  private WorkdayLog findWorkdayLog(TimePunch timePunch) {
    var workdayLog = workdayLogsRepository.findByTimePunch(timePunch.getId());
    if (workdayLog.isEmpty()) {
      throw new WorkdayLogNotFoundException();
    }

    return workdayLog.get();
  }
}
