package br.com.chronos.core.modules.work_schedule.use_cases;

import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkScheduleDto;
import br.com.chronos.core.modules.work_schedule.domain.exceptions.WorkScheduleNotFoundException;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;

public class GetWorkScheduleUseCase {
  private final WorkSchedulesRepository repository;

  public GetWorkScheduleUseCase(WorkSchedulesRepository repository) {
    this.repository = repository;
  }

  public WorkScheduleDto execute(String workScheduleId) {
    var workdayLog = repository.findById(Id.create(workScheduleId));
    if (workdayLog.isEmpty()) {
      throw new WorkScheduleNotFoundException();
    }
    return workdayLog.get().getDto();
  }

}
