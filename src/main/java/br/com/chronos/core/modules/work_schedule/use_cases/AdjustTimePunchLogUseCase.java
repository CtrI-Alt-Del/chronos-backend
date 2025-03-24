package br.com.chronos.core.modules.work_schedule.use_cases;

import java.time.LocalTime;

import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.global.domain.records.Time;
import br.com.chronos.core.modules.work_schedule.domain.dtos.TimePunchDto;
import br.com.chronos.core.modules.work_schedule.domain.entities.TimePunch;
import br.com.chronos.core.modules.work_schedule.domain.exceptions.TimePunchNotFoundException;
import br.com.chronos.core.modules.work_schedule.domain.exceptions.TimePunchNotLoggedException;
import br.com.chronos.core.modules.work_schedule.domain.records.TimePunchPeriod;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.TimePunchesRepository;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkdayLogsRepository;

public class AdjustTimePunchLogUseCase {
  private final WorkdayLogsRepository workdayLogsRepository;

  private final TimePunchesRepository timePunchesRepository;

  public AdjustTimePunchLogUseCase(
      TimePunchesRepository timePunchesRepository,
      WorkdayLogsRepository workdayLogsRepository) {
    this.timePunchesRepository = timePunchesRepository;
    this.workdayLogsRepository = workdayLogsRepository;
  }

  public TimePunchDto execute(String timePunchId, LocalTime adjustedPunchDto, String punchPeriod) {
    var timePunchLog = findTimePunchLog(Id.create(timePunchId));
    timePunchLog.adjust(Time.create(adjustedPunchDto), TimePunchPeriod.create(punchPeriod));
    timePunchesRepository.update(timePunchLog);
    return timePunchLog.getDto();
  }

  private TimePunch findTimePunchLog(Id id) {
    var timePunch = timePunchesRepository.findById(id);
    if (timePunch.isEmpty()) {
      throw new TimePunchNotFoundException();
    }
    var isTimePunchLog = workdayLogsRepository.hasTimePunch(timePunch.get());
    if (isTimePunchLog.isFalse()) {
      throw new TimePunchNotLoggedException();
    }
    return timePunch.get();
  }
}
