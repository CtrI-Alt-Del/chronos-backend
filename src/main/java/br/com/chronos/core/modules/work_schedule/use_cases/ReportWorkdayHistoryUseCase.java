package br.com.chronos.core.modules.work_schedule.use_cases;

import java.time.LocalDate;

import br.com.chronos.core.modules.global.domain.records.Date;
import br.com.chronos.core.modules.global.domain.records.Page;
import br.com.chronos.core.modules.global.responses.PaginationResponse;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkdayLogDto;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkdayLogsRepository;

public class ReportWorkdayHistoryUseCase {
  private final WorkdayLogsRepository repository;

  public ReportWorkdayHistoryUseCase(WorkdayLogsRepository repository) {
    this.repository = repository;
  }

  public PaginationResponse<WorkdayLogDto> execute(LocalDate date, int page) {
    var response = repository.findManyByDate(Date.create(date), Page.create(page));
    var workdayLogs = response.getFirst().map(workdayLog -> workdayLog.getDto());
    var itemsCount = response.getSecond();

    return new PaginationResponse<WorkdayLogDto>(workdayLogs.list(), itemsCount.value());
  }
}
