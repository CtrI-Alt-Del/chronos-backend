package br.com.chronos.core.modules.work_schedule.use_cases;

import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;

public class ResetAllDaysOffSchedulesUseCase {
  private final WorkSchedulesRepository repository;

  public ResetAllDaysOffSchedulesUseCase(WorkSchedulesRepository repository) {
    this.repository = repository;
  }

  public void execute() {
    var workSchedules = repository.findAll();
    for (var workSchedule : workSchedules.list()) {
      workSchedule.resetDaysOffSchedule();
    }
    repository.updateMany(workSchedules);
  }
}
