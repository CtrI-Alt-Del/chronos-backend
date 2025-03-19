package br.com.chronos.core.modules.work_schedule.use_cases;

import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkScheduleDto;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkSchedule;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;

public class CreateWorkScheduleUseCase {
  private final WorkSchedulesRepository repository;

  public CreateWorkScheduleUseCase(WorkSchedulesRepository repository) {
    this.repository = repository;
  }

  public WorkScheduleDto execute(WorkScheduleDto dto) {
    var workSchedule = new WorkSchedule(dto);
    repository.add(workSchedule);
    return workSchedule.getDto();
  }
}
