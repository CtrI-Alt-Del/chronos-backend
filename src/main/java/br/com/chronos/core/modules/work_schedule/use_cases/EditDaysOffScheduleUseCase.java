package br.com.chronos.core.modules.work_schedule.use_cases;

import java.time.LocalDate;
import java.util.List;

import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkSchedule;
import br.com.chronos.core.modules.work_schedule.domain.exceptions.WorkScheduleNotFoundException;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;

public class EditDaysOffScheduleUseCase {
  private final WorkSchedulesRepository repository;

  public EditDaysOffScheduleUseCase(WorkSchedulesRepository repository) {
    this.repository = repository;
  }

  public void execute(String workScheduleId, List<LocalDate> daysOff) {
    var workSchedule = findWorkSchedule(Id.create(workScheduleId));
    workSchedule.replaceDaysOffSchedule(daysOff);
    repository.updateDaysOffSchedule(workSchedule);
  }

  private WorkSchedule findWorkSchedule(Id id) {
    var workSchedule = repository.findById(id);
    if (workSchedule.isEmpty()) {
      throw new WorkScheduleNotFoundException();
    }
    return workSchedule.get();
  }
}
