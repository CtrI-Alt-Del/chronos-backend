package br.com.chronos.core.modules.work_schedule.use_cases;

import java.util.List;

import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.Date;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.work_schedule.domain.dtos.TimePunchDto;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkdayLogDto;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkdayLog;
import br.com.chronos.core.modules.work_schedule.domain.records.CollaboratorSchedule;
import br.com.chronos.core.modules.work_schedule.domain.records.WorkdayStatus;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.DayOffSchedulesRepository;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WeekdaySchedulesRepository;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkdayLogsRepository;

public class CreateTodayWorkdayLogsUseCase {
  private final WorkdayLogsRepository workdayLogsRepository;
  private final WeekdaySchedulesRepository weekdaySchedulesRepository;
  private final DayOffSchedulesRepository dayOffSchedulesRepository;

  public CreateTodayWorkdayLogsUseCase(
      WorkdayLogsRepository workdayLogsRepository,
      WeekdaySchedulesRepository weekdaySchedulesRepository,
      DayOffSchedulesRepository dayOffSchedulesRepository) {
    this.workdayLogsRepository = workdayLogsRepository;
    this.weekdaySchedulesRepository = weekdaySchedulesRepository;
    this.dayOffSchedulesRepository = dayOffSchedulesRepository;
  }

  public void execute(List<String> collaboratorIds) {
    var collaboratorSchedules = findCollaboratorSchedules(collaboratorIds);
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

  private Array<CollaboratorSchedule> findCollaboratorSchedules(List<String> collaboratorIds) {
    Array<CollaboratorSchedule> collaboratorSchedules = Array.createAsEmpty();
    for (var id : collaboratorIds) {
      var collaboratorId = Id.create(id);
      var weekSchedule = weekdaySchedulesRepository.findManyByCollaborator(collaboratorId);
      var dayOffSchedule = dayOffSchedulesRepository.findByCollaborator(collaboratorId);

      var collaboratorSchedule = CollaboratorSchedule.create(
          collaboratorId,
          weekSchedule,
          dayOffSchedule.get());
      collaboratorSchedules.add(collaboratorSchedule);
    }

    return collaboratorSchedules;
  }
}
