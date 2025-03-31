package br.com.chronos.core.modules.work_schedule.use_cases;

import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkSchedule;
import br.com.chronos.core.modules.work_schedule.domain.exceptions.WorkScheduleNotFoundException;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkdayLogsRepository;

public class CreateWorkdayLogUseCase {
  private final WorkdayLogsRepository workdayLogsRepository;
  private final WorkSchedulesRepository workSchedulesRepository;

  public CreateWorkdayLogUseCase(WorkdayLogsRepository workdayLogsRepository,
      WorkSchedulesRepository workSchedulesRepository) {
    this.workdayLogsRepository = workdayLogsRepository;
    this.workSchedulesRepository = workSchedulesRepository;
  }

  public void execute(String collaboratorId, String workScheduleId) {
    var workSchedule = findWorkSchedule(Id.create(workScheduleId));
    var workdayLog = workSchedule.createWorkdayLog(collaboratorId);
    workdayLogsRepository.add(workdayLog);
  }

  private WorkSchedule findWorkSchedule(Id id) {
    var workSchedule = workSchedulesRepository.findById(id);
    if (workSchedule.isEmpty()) {
      throw new WorkScheduleNotFoundException();
    }
    return workSchedule.get();
  }
}
