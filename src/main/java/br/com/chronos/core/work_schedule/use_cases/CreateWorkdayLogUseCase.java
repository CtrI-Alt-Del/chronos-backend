package br.com.chronos.core.work_schedule.use_cases;

import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.work_schedule.domain.records.CollaboratorSchedule;
import br.com.chronos.core.work_schedule.interfaces.repositories.DayOffSchedulesRepository;
import br.com.chronos.core.work_schedule.interfaces.repositories.WeekdaySchedulesRepository;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;

public class CreateWorkdayLogUseCase {
  private final WorkdayLogsRepository workdayLogsRepository;
  private final WeekdaySchedulesRepository weekdaySchedulesRepository;
  private final DayOffSchedulesRepository dayOffSchedulesRepository;

  public CreateWorkdayLogUseCase(
      WorkdayLogsRepository workdayLogsRepository,
      WeekdaySchedulesRepository weekdaySchedulesRepository,
      DayOffSchedulesRepository dayOffSchedulesRepository) {
    this.workdayLogsRepository = workdayLogsRepository;
    this.weekdaySchedulesRepository = weekdaySchedulesRepository;
    this.dayOffSchedulesRepository = dayOffSchedulesRepository;
  }

  public void execute(String collaboratorId) {
    var collaboratorSchedule = findCollaboratorSchedule(Id.create(collaboratorId));
    var workdayLog = collaboratorSchedule.createWorkdayLog(collaboratorId);
    workdayLogsRepository.add(workdayLog);
  }

  private CollaboratorSchedule findCollaboratorSchedule(Id collaboratorId) {
    var weekSchedule = weekdaySchedulesRepository.findManyByCollaborator(collaboratorId);
    var dayOffSchedule = dayOffSchedulesRepository.findByCollaborator(collaboratorId);
    return CollaboratorSchedule.create(collaboratorId, weekSchedule, dayOffSchedule.get());
  }
}
