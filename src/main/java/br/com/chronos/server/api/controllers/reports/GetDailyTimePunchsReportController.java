package br.com.chronos.server.api.controllers.reports;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.chronos.core.work_schedule.domain.dtos.DailyTimePunchsReportDto;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.core.work_schedule.use_cases.GetDailyTimePunchsReportUseCase;

@ReportsController
public class GetDailyTimePunchsReportController {
  @Autowired
  private WorkdayLogsRepository workdayLogsRepository;

  @Autowired
  private jakarta.servlet.http.HttpServletRequest request;

  @GetMapping("/daily-punchs")
  public ResponseEntity<DailyTimePunchsReportDto> execute(
      @RequestParam(value = "date", required = false) String dateStr) {
    System.out.println("[DEBUG CONTROLLER] Query string: " + request.getQueryString());
    System.out.println("[DEBUG CONTROLLER] Valor bruto recebido em 'date': " + dateStr);
    LocalDate date = null;
    if (dateStr != null && !dateStr.isEmpty()) {
      try {
        date = LocalDate.parse(dateStr);
      } catch (Exception e) {
        System.out.println("[DEBUG CONTROLLER] Erro ao converter date: " + e.getMessage());
      }
    }
    var useCase = new GetDailyTimePunchsReportUseCase(workdayLogsRepository);
    var chart = useCase.execute(date);
    return ResponseEntity.ok(chart);
  }
}
