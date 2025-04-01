package br.com.chronos.core.modules.work_schedule.use_cases;

import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.work_schedule.domain.dtos.DayOffScheduleDto;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.DayOffSchedulesRepository;

public class GetDayOffScheduleUseCase {
  private final DayOffSchedulesRepository repository;

  public GetDayOffScheduleUseCase(DayOffSchedulesRepository repository) {
    this.repository = repository;
  }

  public DayOffScheduleDto execute(String collaboratorId) {
    var dayOffSchedule = repository.findByCollaborator(Id.create(collaboratorId));
    return dayOffSchedule.get().getDto();
  }
}
