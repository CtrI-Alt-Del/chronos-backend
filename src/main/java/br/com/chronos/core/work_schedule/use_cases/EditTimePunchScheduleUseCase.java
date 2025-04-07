package br.com.chronos.core.work_schedule.use_cases;

import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.work_schedule.domain.dtos.TimePunchDto;
import br.com.chronos.core.work_schedule.domain.entities.TimePunch;
import br.com.chronos.core.work_schedule.domain.exceptions.TimePunchNotFoundException;
import br.com.chronos.core.work_schedule.domain.exceptions.TimePunchNotScheduledException;
import br.com.chronos.core.work_schedule.interfaces.repositories.TimePunchesRepository;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkSchedulesRepository;

public class EditTimePunchScheduleUseCase {
  private final WorkSchedulesRepository workSchedulesRepository;
  private final TimePunchesRepository timePunchesRepository;

  public EditTimePunchScheduleUseCase(
      TimePunchesRepository timePunchesRepository,
      WorkSchedulesRepository workSchedulesRepository) {
    this.timePunchesRepository = timePunchesRepository;
    this.workSchedulesRepository = workSchedulesRepository;
  }

  public TimePunchDto execute(TimePunchDto timePunchDto) {
    var newTimePunch = new TimePunch(timePunchDto);
    var oldTimePunch = findOldTimePunch(newTimePunch.getId());
    oldTimePunch.replaceWith(newTimePunch);
    timePunchesRepository.update(oldTimePunch);
    return oldTimePunch.getDto();
  }

  private TimePunch findOldTimePunch(Id id) {
    var timePunch = timePunchesRepository.findById(id);
    if (timePunch.isEmpty()) {
      throw new TimePunchNotFoundException();
    }
    var isTimePunchLog = workSchedulesRepository.hasTimePunchSchedule(timePunch.get());
    if (isTimePunchLog.isFalse()) {
      throw new TimePunchNotScheduledException();
    }
    return timePunch.get();
  }
}
