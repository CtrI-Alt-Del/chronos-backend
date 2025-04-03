package br.com.chronos.core.modules.work_schedule.use_cases;

import java.time.LocalDate;

import br.com.chronos.core.modules.global.domain.records.CollaborationSector;
import br.com.chronos.core.modules.global.domain.records.Date;
import br.com.chronos.core.modules.global.domain.records.PageNumber;
import br.com.chronos.core.modules.global.domain.records.Text;
import br.com.chronos.core.modules.global.responses.PaginationResponse;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkdayLogDto;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkdayLogsRepository;

public class ReportWorkdayHistoryUseCase {
  private final WorkdayLogsRepository repository;

  public ReportWorkdayHistoryUseCase(WorkdayLogsRepository repository) {
    this.repository = repository;
  }

  public PaginationResponse<WorkdayLogDto> execute(
      LocalDate date,
      String collaboratorName,
      String collaborationSector,
      int page) {
    var response = repository.findManyByDateAndCollaboratorNameAndCollaborationSector(
        Date.create(date),
        Text.create(collaboratorName, "Nome do colaborador"),
        CollaborationSector.create(collaborationSector),
        PageNumber.create(page));

    var workdayLogs = response.getFirst().map(workdayLog -> workdayLog.getDto());
    var itemsCount = response.getSecond();

    return new PaginationResponse<WorkdayLogDto>(workdayLogs.list(), itemsCount.value());
  }
}
