package br.com.chronos.core.work_schedule.use_cases;

import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.work_schedule.domain.dtos.WorkLeaveDto;
import br.com.chronos.core.work_schedule.domain.records.WorkLeave;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkLeavesRepository;

public class CreateWorkLeaveUseCase {
  private WorkLeavesRepository repository;

  public CreateWorkLeaveUseCase(WorkLeavesRepository repository) {
    this.repository = repository;
  }

  public void execute(WorkLeaveDto dto, String collaboratorId) {
    var workLeave = WorkLeave.create(dto);
    repository.add(workLeave, Id.create(collaboratorId));
  }
}
