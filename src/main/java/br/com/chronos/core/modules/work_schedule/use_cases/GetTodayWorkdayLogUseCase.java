package br.com.chronos.core.modules.work_schedule.use_cases;

import br.com.chronos.core.modules.global.domain.records.Date;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkdayLogDto;
import br.com.chronos.core.modules.work_schedule.domain.entities.WorkdayLog;
import br.com.chronos.core.modules.work_schedule.domain.exceptions.WorkdayLogNotFoundException;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkdayLogsRepository;

public class GetTodayWorkdayLogUseCase {
  private final WorkdayLogsRepository repository;

  public GetTodayWorkdayLogUseCase(WorkdayLogsRepository repository) {
    this.repository = repository;
  }

  public WorkdayLogDto execute(String collaboratorId) {
    var workdayLog = findWorkdayLog(Id.create(collaboratorId));
    return workdayLog.getDto();
  }

  private WorkdayLog findWorkdayLog(Id collaboratorId) {
    var workdayLog = repository.findByCollaboratorAndDate(collaboratorId, Date.createFromNow());
    if (workdayLog.isEmpty()) {
      throw new WorkdayLogNotFoundException();
    }
    return workdayLog.get();
  }
}
