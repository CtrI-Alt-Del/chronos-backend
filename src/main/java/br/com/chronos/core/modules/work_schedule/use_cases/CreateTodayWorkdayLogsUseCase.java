package br.com.chronos.core.modules.work_schedule.use_cases;

import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.Date;
import br.com.chronos.core.modules.work_schedule.domain.dtos.TimePunchDto;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkdayLogDto;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkdayLog;
import br.com.chronos.core.modules.work_schedule.domain.records.CollaboratorSchedule;
import br.com.chronos.core.modules.work_schedule.domain.records.WorkdayStatus;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.CollaboratorSchedulesRepository;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkdayLogsRepository;

public class CreateTodayWorkdayLogsUseCase {
  private final WorkdayLogsRepository workdayLogsRepository;
  private final CollaboratorSchedulesRepository collaboratorSchedulesRepository;

  public CreateTodayWorkdayLogsUseCase(
      WorkdayLogsRepository workdayLogsRepository,
      CollaboratorSchedulesRepository collaboratorSchedulesRepository) {
    this.collaboratorSchedulesRepository = collaboratorSchedulesRepository;
    this.workdayLogsRepository = workdayLogsRepository;
  }

  public void execute() {
    var collaboratorSchedules = collaboratorSchedulesRepository.findAll();
    Array<WorkdayLog> workdayLogs = Array.createAsEmpty();
    // workdayLogsRepository.removeManyByDate(Date.createFromNow());

    for (var collaboratorSchedule : collaboratorSchedules.list()) {
      var workdayStatus = getWorkdayStatus(collaboratorSchedule);
      var workdayLogDto = new WorkdayLogDto()
          .setTimePunchSchedule(collaboratorSchedule.getTodayTimePunchSchedule().getDto())
          .setDate(Date.createFromNow().value())
          .setTimePunchLog(new TimePunchDto())
          .setStatus(workdayStatus.toString())
          .setResponsibleId(collaboratorSchedule.collaboratorId().toString());

      workdayLogs.add(new WorkdayLog(workdayLogDto));
    }
    workdayLogsRepository.addMany(workdayLogs);
  }

  private WorkdayStatus getWorkdayStatus(CollaboratorSchedule collaboratorSchedule) {
    return collaboratorSchedule.getTodayWorkdayStatus();
  }
}
