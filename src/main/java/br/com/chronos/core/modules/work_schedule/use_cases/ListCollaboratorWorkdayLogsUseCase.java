package br.com.chronos.core.modules.work_schedule.use_cases;

import java.time.LocalDate;

import br.com.chronos.core.modules.global.domain.records.DateRange;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.global.domain.records.Page;
import br.com.chronos.core.modules.global.responses.PaginationResponse;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkdayLogDto;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkdayLogsRepository;

public class ListCollaboratorWorkdayLogsUseCase {
  private final WorkdayLogsRepository repository;

  public ListCollaboratorWorkdayLogsUseCase(WorkdayLogsRepository repository) {
    this.repository = repository;
  }

  public PaginationResponse<WorkdayLogDto> execute(
      String collaboratorId,
      LocalDate startDate,
      LocalDate endDate,
      int page) {

    var response = repository.findManyByCollaboratorAndDateRange(
        Id.create(collaboratorId),
        DateRange.create(startDate, endDate),
        Page.create(page));
    var workdayLogs = response.getFirst().map((workdayLog) -> workdayLog.getDto());
    var itemsCount = response.getSecond();

    return new PaginationResponse<WorkdayLogDto>(workdayLogs.list(), itemsCount.value());
  }
}
