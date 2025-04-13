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

public class AdjustTimePunchUseCase {
  private final WorkdayLogsRepository workdayLogsRepository;
  private final TimePunchesRepository timePunchesRepository;

  public AdjustTimePunchUseCase(
      TimePunchesRepository timePunchesRepository,
      WorkdayLogsRepository workdayLogsRepository) {
    this.timePunchesRepository = timePunchesRepository;
    this.workdayLogsRepository = workdayLogsRepository;
  }

  public TimePunchDto execute(String timePunchId, LocalTime time, String period) {
    var timePunch = findTimePunch(Id.create(timePunchId));
    timePunch.adjust(Time.create(time), TimePunchPeriod.create(period));
    timePunchesRepository.update(timePunch);
    return timePunch.getDto();
  }

  private TimePunch findTimePunch(Id id) {
    var timePunch = timePunchesRepository.findById(id);
    if (timePunch.isEmpty()) {
      throw new TimePunchNotFoundException();
    }
    var isTimePunch = workdayLogsRepository.hasTimePunch(timePunch.get());
    if (isTimePunch.isFalse()) {
      throw new TimePunchNotLoggedException();
    }
    return timePunch.get();
  }
}
