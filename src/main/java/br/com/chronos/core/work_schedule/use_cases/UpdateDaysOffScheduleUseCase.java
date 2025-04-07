package br.com.chronos.core.work_schedule.use_cases;

import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.work_schedule.domain.dtos.DayOffScheduleDto;
import br.com.chronos.core.work_schedule.domain.entities.DayOffSchedule;
import br.com.chronos.core.work_schedule.interfaces.repositories.DayOffSchedulesRepository;

public class UpdateDaysOffScheduleUseCase {
  private final DayOffSchedulesRepository repository;

  public UpdateDaysOffScheduleUseCase(DayOffSchedulesRepository repository) {
    this.repository = repository;
  }

  public void execute(String collaboratorId, DayOffScheduleDto dayOffScheduleDto) {
    System.out.println("workdaysCount: " + dayOffScheduleDto.workdaysCount);
    System.out.println("daysOffCount  : " + dayOffScheduleDto.daysOffCount);
    System.out.println("daysOff  : " + dayOffScheduleDto.daysOff);
    var dayOffSchedule = new DayOffSchedule(dayOffScheduleDto);
    repository.replace(dayOffSchedule, Id.create(collaboratorId));
  }

}
