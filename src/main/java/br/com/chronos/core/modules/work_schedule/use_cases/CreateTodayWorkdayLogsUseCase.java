package br.com.chronos.core.modules.work_schedule.use_cases;

import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.Date;
import br.com.chronos.core.modules.work_schedule.domain.dtos.TimePunchDto;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkdayLogDto;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkSchedule;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkdayLog;
import br.com.chronos.core.modules.work_schedule.domain.records.WorkdayStatus;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkdayLogsRepository;

public class CreateTodayWorkdayLogsUseCase {
  private final WorkdayLogsRepository workdayLogsRepository;
  private final WorkSchedulesRepository workSchedulesRepository;

  public CreateTodayWorkdayLogsUseCase(
      WorkdayLogsRepository workdayLogsRepository,
      WorkSchedulesRepository workSchedulesRepository) {
    this.workSchedulesRepository = workSchedulesRepository;
    this.workdayLogsRepository = workdayLogsRepository;
  }

  public void execute() {
    var workSchedules = workSchedulesRepository.findAllWithAnyCollaborator();
    Array<WorkdayLog> workdayLogs = Array.createAsEmpty();
    // workdayLogsRepository.removeManyByDate(Date.createFromNow());

    for (var workSchedule : workSchedules.list()) {
      var workdayStatus = getWorkdayStatus(workSchedule);

      var collaboratorIds = workSchedulesRepository.findCollaboratorIdsByWorkSchedule(workSchedule);
      workdayLogs = collaboratorIds.map((collaboratorId) -> {
        var workdayLogDto = new WorkdayLogDto()
            .setTimePunchSchedule(workSchedule.getTodayTimePunchSchedule().getDto())
            .setDate(Date.createFromNow().value())
            .setTimePunchLog(new TimePunchDto())
            .setStatus(workdayStatus.toString())
            .setResponsibleId(collaboratorId.toString());

        return new WorkdayLog(workdayLogDto);
      });
    }
    System.out.println(workdayLogs);
    workdayLogsRepository.addMany(workdayLogs);
  }

  private WorkdayStatus getWorkdayStatus(WorkSchedule workSchedule) {
    return workSchedule.getTodayWorkdayStatus();
  }
}
