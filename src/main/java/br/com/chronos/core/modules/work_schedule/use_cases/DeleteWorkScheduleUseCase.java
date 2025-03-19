package br.com.chronos.core.modules.work_schedule.use_cases;

import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkSchedule;
import br.com.chronos.core.modules.work_schedule.domain.exceptions.WorkScheduleNotFoundException;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;

public class DeleteWorkScheduleUseCase {
  private final WorkSchedulesRepository repository;

  public DeleteWorkScheduleUseCase(WorkSchedulesRepository repository) {
    this.repository = repository;
  }

  public void execute(String workScheduleId) {
    var workSchedule = findWorkSchedule(Id.create(workScheduleId));
    repository.remove(workSchedule);
  }

  private WorkSchedule findWorkSchedule(Id id) {
    var workSchedule = repository.findByIdAndWithoutCollaborator(id);
    if (workSchedule.isEmpty()) {
      throw new WorkScheduleNotFoundException();
    }
    return workSchedule.get();
  }
}
