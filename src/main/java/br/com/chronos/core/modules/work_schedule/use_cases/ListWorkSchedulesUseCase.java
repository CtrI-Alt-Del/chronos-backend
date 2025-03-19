package br.com.chronos.core.modules.work_schedule.use_cases;

import br.com.chronos.core.modules.global.domain.records.Page;
import br.com.chronos.core.modules.global.responses.PaginationResponse;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkScheduleDto;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;

public class ListWorkSchedulesUseCase {
  private final WorkSchedulesRepository repository;

  public ListWorkSchedulesUseCase(WorkSchedulesRepository repository) {
    this.repository = repository;
  }

  public PaginationResponse<WorkScheduleDto> execute(int page) {
    var response = repository.findMany(Page.create(page));
    var workSchedules = response.getFirst().map(workSchedule -> workSchedule.getDto());
    var itemsCount = response.getSecond();

    return new PaginationResponse<WorkScheduleDto>(
        workSchedules.list(),
        itemsCount.value());
  }
}
