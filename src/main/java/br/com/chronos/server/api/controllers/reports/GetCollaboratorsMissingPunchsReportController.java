
package br.com.chronos.server.api.controllers.reports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.chronos.core.work_schedule.domain.dtos.CollaboratorsMissingTimePunchReportDto;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.core.work_schedule.use_cases.GetCollaboratorQuantityMissingPunchsReportUseCase;

@ReportsController
public class GetCollaboratorsMissingPunchsReportController {
  @Autowired
  private WorkdayLogsRepository workdayLogsRepository;

  @GetMapping("/collaborators-missing-time")
  public ResponseEntity<CollaboratorsMissingTimePunchReportDto> execute() {
    var useCase = new GetCollaboratorQuantityMissingPunchsReportUseCase(workdayLogsRepository);
    var chart = useCase.execute();
    return ResponseEntity.ok(chart);
  }
}
