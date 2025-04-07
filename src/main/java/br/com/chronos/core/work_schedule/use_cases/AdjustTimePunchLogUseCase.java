package br.com.chronos.core.work_schedule.use_cases;

import java.time.LocalTime;

import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.Time;
import br.com.chronos.core.work_schedule.domain.dtos.TimePunchDto;
import br.com.chronos.core.work_schedule.domain.entities.TimePunch;
import br.com.chronos.core.work_schedule.domain.exceptions.TimePunchNotFoundException;
import br.com.chronos.core.work_schedule.domain.exceptions.TimePunchNotLoggedException;
import br.com.chronos.core.work_schedule.domain.records.TimePunchPeriod;
import br.com.chronos.core.work_schedule.interfaces.repositories.TimePunchesRepository;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;

public class AdjustTimePunchLogUseCase {
  private final WorkdayLogsRepository workdayLogsRepository;
  private final TimePunchesRepository timePunchesRepository;

  public AdjustTimePunchLogUseCase(
      TimePunchesRepository timePunchesRepository,
      WorkdayLogsRepository workdayLogsRepository) {
    this.timePunchesRepository = timePunchesRepository;
    this.workdayLogsRepository = workdayLogsRepository;
  }

  public TimePunchDto execute(String timePunchId, LocalTime time, String period) {
    var timePunchLog = findTimePunchLog(Id.create(timePunchId));
    timePunchLog.adjust(Time.create(time), TimePunchPeriod.create(period));
    timePunchesRepository.update(timePunchLog);
    return timePunchLog.getDto();
  }

  private TimePunch findTimePunchLog(Id id) {
    var timePunch = timePunchesRepository.findById(id);
    if (timePunch.isEmpty()) {
      throw new TimePunchNotFoundException();
    }
    var isTimePunchLog = workdayLogsRepository.hasTimePunchLog(timePunch.get());
    if (isTimePunchLog.isFalse()) {
      throw new TimePunchNotLoggedException();
    }
    return timePunch.get();
  }
}
