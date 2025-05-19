
package br.com.chronos.core.work_schedule.use_cases;

import java.time.LocalDate;

import br.com.chronos.core.global.domain.records.DateRange;
import br.com.chronos.core.work_schedule.domain.dtos.WorkdayStatusChartoDto;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;

public class GetWorkdayStatusChartUseCase {
  private final WorkdayLogsRepository repository;

  public GetWorkdayStatusChartUseCase(WorkdayLogsRepository repository) {
    this.repository = repository;
  }

  public WorkdayStatusChartoDto execute(LocalDate startDate, LocalDate endDate) {
    var workDayStatusChartDto = repository
        .getWorkdayStatusChartByDateRange(DateRange.createWithMonthRange(startDate, endDate));
    return workDayStatusChartDto;
  }

}
