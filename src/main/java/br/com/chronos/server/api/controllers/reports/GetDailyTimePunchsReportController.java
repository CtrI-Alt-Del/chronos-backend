
package br.com.chronos.server.api.controllers.reports;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.chronos.core.work_schedule.domain.dtos.DailyTimePunchsReportDto;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.core.work_schedule.use_cases.GetDailyTimePunchsReportUseCase;

@ReportsController
public class GetDailyTimePunchsReportController {
  @Autowired
  private WorkdayLogsRepository workdayLogsRepository;

  @GetMapping("/daily-punchs")
  public ResponseEntity<DailyTimePunchsReportDto> execute(
      @RequestParam(value = "date", required = false) LocalDate date) {
    var useCase = new GetDailyTimePunchsReportUseCase(workdayLogsRepository);
    var chart = useCase.execute(date);
    return ResponseEntity.ok(chart);
  }
}
