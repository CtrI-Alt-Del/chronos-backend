package br.com.chronos.core.modules.work_schedule.use_cases;

import java.time.LocalDate;
import java.util.List;

import br.com.chronos.core.modules.global.domain.records.Date;
import br.com.chronos.core.modules.work_schedule.domain.dtos.WorkdayLogDto;
import br.com.chronos.core.modules.work_schedule.domain.records.Page;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkdayLogsRepository;

public class ReportWorkdayHistoryUseCase {
  private final WorkdayLogsRepository repository;

  public ReportWorkdayHistoryUseCase(WorkdayLogsRepository repository) {
    this.repository = repository;
  }

  public List<WorkdayLogDto> execute(LocalDate date, int page) {
    var workdayLogs = repository.findManyByDate(Date.create(date), Page.create(page));
    return workdayLogs.map((workdayLog) -> workdayLog.getDto()).list();
  }
}
