package br.com.chronos.core.modules.work_schedule.use_cases;

import java.util.List;

import br.com.chronos.core.modules.global.domain.records.Page;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkScheduleDto;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;

public class ListWorkSchedulesUseCase {
  private final WorkSchedulesRepository repository;

  public ListWorkSchedulesUseCase(WorkSchedulesRepository repository) {
    this.repository = repository;
  }

  public List<WorkScheduleDto> execute(int page) {
    var workSchedules = repository.findMany(Page.create(page));
    return workSchedules.map(workSchedule -> workSchedule.getDto()).list();
  }
}
