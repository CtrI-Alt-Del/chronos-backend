package br.com.chronos.core.work_schedule.use_cases;

import java.time.LocalDate;
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

public class CreateWorkdayLogsUseCase {
  private final WorkdayLogsRepository workdayLogsRepository;
  private final DayOffSchedulesRepository dayOffSchedulesRepository;

  public CreateWorkdayLogsUseCase(
      WorkdayLogsRepository workdayLogsRepository,
      DayOffSchedulesRepository dayOffSchedulesRepository) {
    this.workdayLogsRepository = workdayLogsRepository;
    this.dayOffSchedulesRepository = dayOffSchedulesRepository;
  }

  public void execute(List<String> collaboratorIds, List<Integer> collaboratorWorkloads, LocalDate date) {
    Array<WorkdayLog> workdayLogs = Array.createAsEmpty();
    var workday = Date.create(date);
    workdayLogsRepository.removeManyByDate(workday);

    for (var index = 0; index < collaboratorIds.size(); index++) {
      var id = collaboratorIds.get(index);
      var workload = collaboratorWorkloads.get(index);
      var collaboratorId = Id.create(id);
      var workdayStatus = getWorkdayStatus(collaboratorId);
      var workdayLogDto = new WorkdayLogDto()
          .setDate(workday.value())
          .setWorkloadSchedule((byte) (int) workload)
          .setStatus(workdayStatus.toString())
          .setTimePunch(new TimePunchDto())
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
