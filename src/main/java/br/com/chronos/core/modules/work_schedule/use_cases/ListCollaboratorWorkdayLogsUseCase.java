package br.com.chronos.core.modules.work_schedule.use_cases;

import java.time.LocalDate;
import java.util.List;

import br.com.chronos.core.modules.global.domain.records.DateRange;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkdayLogDto;
import br.com.chronos.core.modules.work_schedule.domain.records.Page;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkdayLogsRepository;

public class ListCollaboratorWorkdayLogsUseCase {
  private final WorkdayLogsRepository repository;

  public ListCollaboratorWorkdayLogsUseCase(WorkdayLogsRepository repository) {
    this.repository = repository;
  }

  public List<WorkdayLogDto> execute(
      String collaboratorId,
      LocalDate startDate,
      LocalDate endDate,
      int page) {

    var workdayLogs = repository.findManyByCollaboratorAndDateRange(
        Id.create(collaboratorId),
        DateRange.create(startDate, endDate),
        Page.create(page));

    return workdayLogs.map((workdayLog) -> workdayLog.getDto()).list();
  }
}
