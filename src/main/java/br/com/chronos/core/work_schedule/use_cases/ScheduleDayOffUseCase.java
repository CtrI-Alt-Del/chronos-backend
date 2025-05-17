package br.com.chronos.core.work_schedule.use_cases;

import java.time.LocalDate;

import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.work_schedule.domain.entities.DayOffSchedule;
import br.com.chronos.core.work_schedule.domain.events.DayOffScheduleNotFoundException;
import br.com.chronos.core.work_schedule.interfaces.repositories.DayOffSchedulesRepository;

public class ScheduleDayOffUseCase {
  private final DayOffSchedulesRepository repository;

  public ScheduleDayOffUseCase(DayOffSchedulesRepository repository) {
    this.repository = repository;
  }

  public void execute(LocalDate dayOff, String collaboratorIdValue) {
    var collaboratorId = Id.create(collaboratorIdValue);
    var dayOffSchedule = findDayOffSchedule(collaboratorId);
    dayOffSchedule.schedule(Date.create(dayOff));
    repository.replace(dayOffSchedule, collaboratorId);
  }

  public DayOffSchedule findDayOffSchedule(Id collaboratorId) {
    var dayOffSchedule = repository.findByCollaborator(collaboratorId);
    if (dayOffSchedule.isEmpty()) {
      throw new DayOffScheduleNotFoundException();
    }
    return dayOffSchedule.get();
  }
}
