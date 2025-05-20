package br.com.chronos.server.api.controllers.reports;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.chronos.core.work_schedule.domain.dtos.WorkdayStatusChartoDto;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.core.work_schedule.use_cases.GetWorkdayStatusChartUseCase;

@ReportsController
public class GetWorkdayStatusChartController {
  @Autowired
  private WorkdayLogsRepository workdayLogsRepository;

  @GetMapping("/workday-status")
  public ResponseEntity<WorkdayStatusChartoDto> execute(
      @RequestParam(value = "startDate", required = false) LocalDate startDate,
      @RequestParam(value = "endDate", required = false) LocalDate endDate) {
    var useCase = new GetWorkdayStatusChartUseCase(workdayLogsRepository);
    var chart = useCase.execute(startDate, endDate);
    return ResponseEntity.ok(chart);
  }
}
