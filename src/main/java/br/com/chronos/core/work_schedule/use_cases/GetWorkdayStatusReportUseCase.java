
package br.com.chronos.core.work_schedule.use_cases;

import java.time.LocalDate;

import br.com.chronos.core.global.domain.records.DateRange;
import br.com.chronos.core.work_schedule.domain.dtos.WorkdayStatusReportDto;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;

public class GetWorkdayStatusReportUseCase {
  private final WorkdayLogsRepository repository;

  public GetWorkdayStatusReportUseCase(WorkdayLogsRepository repository) {
    this.repository = repository;
  }

  public WorkdayStatusReportDto execute(LocalDate startDate, LocalDate endDate) {
    var workDayStatusChartValues = repository
        .getCollaboratorsWorkdayStatusByDateRange(DateRange.create(startDate, endDate, 30));
    var activeCollaboratos = workDayStatusChartValues.getFirst();
    var vacationCollaborators = workDayStatusChartValues.getSecond();
    var withdrawCollaborators = workDayStatusChartValues.getThird();
    return WorkdayStatusReportDto.createFromValues(activeCollaboratos, vacationCollaborators, withdrawCollaborators);
  }

}
