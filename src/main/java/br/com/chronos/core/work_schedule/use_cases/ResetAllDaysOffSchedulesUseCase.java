package br.com.chronos.core.work_schedule.use_cases;

import br.com.chronos.core.work_schedule.interfaces.repositories.DayOffSchedulesRepository;

public class ResetAllDaysOffSchedulesUseCase {
  private final DayOffSchedulesRepository repository;

  public ResetAllDaysOffSchedulesUseCase(DayOffSchedulesRepository repository) {
    this.repository = repository;
  }

  public void execute() {
    var dayOffSchedules = repository.findAll();
    for (var dayOffSchedule : dayOffSchedules.list()) {
      dayOffSchedule.resetDaysOffSchedule();
    }
    repository.replaceMany(dayOffSchedules);
  }
}
