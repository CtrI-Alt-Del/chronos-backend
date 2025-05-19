
package br.com.chronos.core.work_schedule.use_cases;

import java.time.LocalDate;

import br.com.chronos.core.work_schedule.domain.dtos.YearlyAbsenceChartDto;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;

public class GetYearlyAbsenceChartUseCase {
  private final WorkdayLogsRepository repository;

  public GetYearlyAbsenceChartUseCase(WorkdayLogsRepository repository) {
    this.repository = repository;
  }

  public YearlyAbsenceChartDto execute(LocalDate startDate, LocalDate endDate) {
    var workDayStatusChartDto = repository
        .getYearlyAbsenceChart();
    return workDayStatusChartDto;
  }

}
