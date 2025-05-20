
package br.com.chronos.core.work_schedule.use_cases;

import java.time.LocalDate;

import br.com.chronos.core.work_schedule.domain.dtos.YearlyAbsenceReportDto;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;

public class GetYearlyAbsenceReportUseCase {
  private final WorkdayLogsRepository repository;

  public GetYearlyAbsenceReportUseCase(WorkdayLogsRepository repository) {
    this.repository = repository;
  }

  public YearlyAbsenceReportDto execute(LocalDate startDate, LocalDate endDate) {
    var chartValues = repository
        .getYearlyCollaboratorsAbsence();
    return YearlyAbsenceReportDto.createFromValues(chartValues.list());
  }

}
