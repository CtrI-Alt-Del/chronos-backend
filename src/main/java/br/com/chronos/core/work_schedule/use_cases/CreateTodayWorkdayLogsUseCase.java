package br.com.chronos.core.work_schedule.use_cases;

import java.util.List;

import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.work_schedule.domain.dtos.TimePunchDto;
import br.com.chronos.core.work_schedule.domain.dtos.WorkdayLogDto;
import br.com.chronos.core.work_schedule.domain.entities.WorkdayLog;
import br.com.chronos.core.work_schedule.domain.records.WorkdayStatus;
import br.com.chronos.core.work_schedule.interfaces.repositories.DayOffSchedulesRepository;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;

public class CreateTodayWorkdayLogsUseCase {
  private final WorkdayLogsRepository workdayLogsRepository;
  private final DayOffSchedulesRepository dayOffSchedulesRepository;

  public CreateTodayWorkdayLogsUseCase(
      WorkdayLogsRepository workdayLogsRepository,
      DayOffSchedulesRepository dayOffSchedulesRepository) {
    this.workdayLogsRepository = workdayLogsRepository;
    this.dayOffSchedulesRepository = dayOffSchedulesRepository;
  }

  public void execute(List<String> collaboratorIds) {
    Array<WorkdayLog> workdayLogs = Array.createAsEmpty();
    // workdayLogsRepository.removeManyByDate(Date.createFromNow());

    for (var id : collaboratorIds) {
      var collaboratorId = Id.create(id);
      var workdayStatus = getWorkdayStatus(collaboratorId);
      var workdayLogDto = new WorkdayLogDto()
          .setDate(Date.createFromNow().value())
          .setTimePunch(new TimePunchDto())
          .setStatus(workdayStatus.toString())
          .setResponsibleId(collaboratorId.toString());

      workdayLogs.add(new WorkdayLog(workdayLogDto));
    }
    workdayLogsRepository.addMany(workdayLogs);
  }

  private WorkdayStatus getWorkdayStatus(Id collaboratorId) {
    var dayOffSchedule = dayOffSchedulesRepository.findByCollaborator(collaboratorId);
    if (dayOffSchedule.isPresent() && dayOffSchedule.get().isTodayDayOff().isTrue()) {
      return WorkdayStatus.createAsDayOff();
    }
    return WorkdayStatus.createAsNormalDay();
  }
}
