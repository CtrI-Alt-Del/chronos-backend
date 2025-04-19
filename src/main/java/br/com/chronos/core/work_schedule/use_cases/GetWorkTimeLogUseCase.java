package br.com.chronos.core.work_schedule.use_cases;

import br.com.chronos.core.global.domain.records.DateRange;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.work_schedule.domain.dtos.WorkTimeLogDto;
import br.com.chronos.core.work_schedule.domain.records.WorkTimeLog;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;

public class GetWorkTimeLogUseCase {
  private final WorkdayLogsRepository repository;

  public GetWorkTimeLogUseCase(WorkdayLogsRepository repository) {
    this.repository = repository;
  }

  public WorkTimeLogDto execute(String collaboratorId) {
    var dateRange = DateRange.createAsCurrentMonthRange();
    var workdayLogs = repository.findAllByCollaboratorAndDateRange(Id.create(collaboratorId), dateRange);
    var workTimeLog = WorkTimeLog.create(workdayLogs);
    return workTimeLog.getDto();
  }
}
