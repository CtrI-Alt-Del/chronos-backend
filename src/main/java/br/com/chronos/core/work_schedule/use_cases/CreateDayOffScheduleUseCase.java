package br.com.chronos.core.work_schedule.use_cases;

import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.work_schedule.domain.dtos.DayOffScheduleDto;
import br.com.chronos.core.work_schedule.domain.entities.DayOffSchedule;
import br.com.chronos.core.work_schedule.interfaces.repositories.DayOffSchedulesRepository;

public class CreateDayOffScheduleUseCase {
  private final DayOffSchedulesRepository repository;

  public CreateDayOffScheduleUseCase(DayOffSchedulesRepository repository) {
    this.repository = repository;
  }

  public void execute(String collaboratorId, DayOffScheduleDto dayOffScheduleDto) {
    var dayOffSchedule = new DayOffSchedule(dayOffScheduleDto);
    repository.add(dayOffSchedule, Id.create(collaboratorId));
  }
}