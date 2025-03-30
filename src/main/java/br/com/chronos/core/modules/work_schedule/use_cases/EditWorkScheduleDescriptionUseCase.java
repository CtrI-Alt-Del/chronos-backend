package br.com.chronos.core.modules.work_schedule.use_cases;

import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkSchedule;
import br.com.chronos.core.modules.work_schedule.domain.exceptions.WorkScheduleNotFoundException;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;

public class EditWorkScheduleDescriptionUseCase {
  private final WorkSchedulesRepository repository;

  public EditWorkScheduleDescriptionUseCase(WorkSchedulesRepository repository) {
    this.repository = repository;
  }

  public void execute(String id, String description) {
    var workSchedule = findWorkSchedule(Id.create(id));
    workSchedule.updateDescription(description);
    repository.update(workSchedule);
  }

  private WorkSchedule findWorkSchedule(Id id) {
    var workSchedule = repository.findById(id);
    if (workSchedule.isEmpty()) {
      throw new WorkScheduleNotFoundException();
    }
    return workSchedule.get();
  }
}
