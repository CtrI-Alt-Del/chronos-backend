package br.com.chronos.core.modules.work_schedule.use_cases;

import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkdayLogDto;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkSchedule;
import br.com.chronos.core.modules.work_schedule.domain.records.WorkdayStatus;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;

public class CreateTodayWorkdayLogsUseCase {
  private final WorkSchedulesRepository repository;

  public CreateTodayWorkdayLogsUseCase(WorkSchedulesRepository repository) {
    this.repository = repository;
  }

  public void execute() {
    var workSchedules = repository.findAllCollaboratorWorkSchedules();

    for (var workSchedule : workSchedules.list()) {
      var workdayLogDto = new WorkdayLogDto()
          .setTimePunchSchedule(workSchedule.timePunchSchedule().getDto())
          .setStatus(null);

    }
  }

  private WorkdayStatus getWorkdayStatus(WorkSchedule workSchedule) {

    return WorkdayStatus.create(null);
  }
}
