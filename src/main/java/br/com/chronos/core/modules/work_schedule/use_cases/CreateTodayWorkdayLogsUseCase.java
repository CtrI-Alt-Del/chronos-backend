package br.com.chronos.core.modules.work_schedule.use_cases;

import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkdayLogDto;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkdayLog;
import br.com.chronos.core.modules.work_schedule.domain.records.CollaboratorWorkSchedule;
import br.com.chronos.core.modules.work_schedule.domain.records.WorkdayStatus;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkdayLogsRepository;

public class CreateTodayWorkdayLogsUseCase {
  private final WorkSchedulesRepository workSchedulesRepository;
  private final WorkdayLogsRepository workdayLogsRepository;

  public CreateTodayWorkdayLogsUseCase(WorkSchedulesRepository workSchedulesRepository,
      WorkdayLogsRepository workdayLogsRepository) {
    this.workSchedulesRepository = workSchedulesRepository;
    this.workdayLogsRepository = workdayLogsRepository;
  }

  public void execute() {
    var workSchedules = workSchedulesRepository.findAllCollaboratorWorkSchedules();
    Array<WorkdayLog> workdayLogs = Array.createAsEmpty();

    for (var workSchedule : workSchedules.list()) {
      var workdayLogDto = new WorkdayLogDto()
          .setTimePunchSchedule(workSchedule.timePunchSchedule().getDto())
          .setStatus(getWorkdayStatus(workSchedule).toString())
          .setResponsible(workSchedule.collaboratorId().toString());

      var workdayLog = new WorkdayLog(workdayLogDto);
      workdayLogs.add(workdayLog);
    }

    workdayLogsRepository.addMany(workdayLogs);
  }

  private WorkdayStatus getWorkdayStatus(CollaboratorWorkSchedule collaboratorWorkSchedule) {
    return collaboratorWorkSchedule.workSchedule().getWorkdayStatus();
  }
}
