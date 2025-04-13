package br.com.chronos.core.work_schedule.use_cases;

import java.time.LocalDate;

import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.PageNumber;
import br.com.chronos.core.global.domain.records.Text;
import br.com.chronos.core.global.responses.PaginationResponse;
import br.com.chronos.core.work_schedule.domain.dtos.WorkdayLogDto;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;

public class GetCollaborationSectorHistoryUseCase {
  private final WorkdayLogsRepository repository;

  public GetCollaborationSectorHistoryUseCase(WorkdayLogsRepository repository) {
    this.repository = repository;
  }

  public PaginationResponse<WorkdayLogDto> execute(
      LocalDate date,
      String collaboratorName,
      String collaborationSector,
      int page) {
    var response = repository.findManyByDateAndCollaborationSector(
        Date.create(date),
        Text.create(collaboratorName, "Nome do colaborador"),
        CollaborationSector.create(collaborationSector),
        PageNumber.create(page));

    var workdayLogs = response.getFirst().map(workdayLog -> workdayLog.getDto());
    var itemsCount = response.getSecond();

    return new PaginationResponse<WorkdayLogDto>(workdayLogs.list(), itemsCount.value());
  }
}
