
package br.com.chronos.core.work_schedule.use_cases;

import java.time.LocalDate;

import br.com.chronos.core.work_schedule.domain.dtos.CollaboratorsMissingTimePunchReportDto;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;

public class GetCollaboratorQuantityMissingPunchsReportUseCase {
  private final WorkdayLogsRepository repository;

  public GetCollaboratorQuantityMissingPunchsReportUseCase(WorkdayLogsRepository repository) {
    this.repository = repository;
  }

  public CollaboratorsMissingTimePunchReportDto execute() {
    var chartValues = repository
        .getCollaboratorsQuantityWithoutPunchsFromLastSevenDays();
    return CollaboratorsMissingTimePunchReportDto.createFromValues(chartValues.list());
  }

}
